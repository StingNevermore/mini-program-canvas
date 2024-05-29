package com.nevermore.mpc.infra.utils;

import com.nevermore.mpc.infra.lambda.ThrowableBiConsumer;
import com.nevermore.mpc.infra.lambda.ThrowableBiFunction;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.throwIfUnchecked;

/**
 * @author nevermore
 * @since 0.0.1
 */
public class ZkBasedNodeResource<T> implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(ZkBasedNodeResource.class);

    private final Object lock = new Object();

    private volatile T resource;
    private volatile boolean closed = false;
    private volatile boolean emptyLogged = false;
    private volatile boolean hasAddedListener = false;
    private final AtomicBoolean outdated = new AtomicBoolean(false);
    private CuratorCache cache;

    private final String path;
    private final Supplier<T> defaultResourceSupplier;
    private final ThrowableBiFunction<byte[], Stat, T, ? extends Exception> deserializer;
    private final ThrowableBiConsumer<ChildData, ChildData, ? extends Exception> onResourceChangedListener;
    private final Supplier<CuratorFramework> curatorFactory;

    private ZkBasedNodeResource(Builder<T> builder) {
        path = builder.path;
        this.defaultResourceSupplier = builder.defaultResourceSupplier;
        this.deserializer = builder.deserializer;
        this.curatorFactory = builder.curatorFactory;
        this.onResourceChangedListener = builder.onResourceChangedListener;
    }

    public static <E> Builder<E> newBuilder(String path) {
        return new Builder<>(path);
    }

    public T get() {
        checkClosed();
        if (resource == null) {
            synchronized (lock) {
                if (resource == null) {
                    cache = CuratorCache.build(curatorFactory.get(), path);
                    Stat stat;
                    byte[] data;
                    try {
                        stat = curatorFactory.get().checkExists().forPath(path);
                        if (stat == null) {
                            tryLogEmpty();
                            return defaultResourceSupplier.get();
                        }
                        data = curatorFactory.get().getData().forPath(path);
                        if (data == null) {
                            tryLogEmpty();
                            return defaultResourceSupplier.get();
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                    tryAddListener();
                    try {
                        resource = deserializer.apply(data, stat);
                    } catch (Exception e) {
                        logger.error("Deserialization failed, path: {}", path);
                        throwIfUnchecked(e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (outdated.get()) {
            throw new IllegalStateException("resource has been outdated");
        }
        return resource;
    }

    @Override
    public void close() throws IOException {
        safeClose(cache);
        safeClose(curatorFactory.get());
        closed = true;
    }

    private void tryLogEmpty() {
        if (!emptyLogged) {
            logger.warn("zk path: {}:{} not found, or data is empty, using empty data",
                    zkConnectionString(curatorFactory.get()),
                    path);
            emptyLogged = true;
        }
    }

    private void tryAddListener() {
        if (!hasAddedListener) {
            CuratorCacheListener listener = CuratorCacheListener.builder()
                    .forCreates(childData -> handle(emptyChildData(), childData))
                    .forChanges(this::handle)
                    .forDeletes(oldData -> handle(oldData, emptyChildData()))
                    .build();
            cache.listenable().addListener(listener);
        }

        hasAddedListener = true;
    }

    private void handle(ChildData oldData, ChildData newData) {
        notifyChangeQuietly(oldData, newData);
        byte[] data = newData.getData();
        T resourceData;
        try {
            resourceData = deserializer.apply(data, newData.getStat());
        } catch (Exception e) {
            outdated.compareAndSet(false, true);
            logger.error("deserialization error, resource maybe outdated!, path: {}", path);
            throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        synchronized (lock) {
            resource = resourceData;
            outdated.compareAndSet(true, false);
        }
    }

    private ChildData emptyChildData() {
        return new ChildData(path, null, null);
    }

    private void notifyChangeQuietly(ChildData oldNode, ChildData node) {
        try {
            onResourceChangedListener.accept(oldNode, node);
        } catch (Exception e) {
            logger.error("Failed to notify listener, path: %s".formatted(path), e);
        }
    }

    private static String zkConnectionString(CuratorFramework curatorFramework) {
        return curatorFramework.getZookeeperClient().getCurrentConnectionString();
    }

    private void checkClosed() {
        if (closed) {
            throw new IllegalStateException("Resource is closed");
        }
    }

    private static void safeClose(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static class Builder<E> {
        private final String path;
        private Supplier<E> defaultResourceSupplier;
        private ThrowableBiFunction<byte[], Stat, E, ? extends Exception> deserializer;
        private Supplier<CuratorFramework> curatorFactory;
        private ThrowableBiConsumer<ChildData, ChildData, ? extends Exception> onResourceChangedListener
                = (e1, e2) -> {
        };

        public Builder(String path) {
            this.path = path;
        }

        public Builder<E> withDefaultResourceSupplier(Supplier<E> defaultResourceSupplier) {
            this.defaultResourceSupplier = defaultResourceSupplier;
            return this;
        }

        public Builder<E> withDeserializer(ThrowableBiFunction<byte[], Stat, E, ? extends Exception> deserializer) {
            this.deserializer = deserializer;
            return this;
        }

        public Builder<E> withCuratorFactory(Supplier<CuratorFramework> curatorFactory) {
            this.curatorFactory = curatorFactory;
            return this;
        }

        public Builder<E> withOnResourceChangedListener(
                ThrowableBiConsumer<ChildData, ChildData, ? extends Exception> onResourceChangedListener) {
            this.onResourceChangedListener = onResourceChangedListener;
            return this;
        }

        private void ensure() {
            checkNotNull(path, "path");
            checkNotNull(defaultResourceSupplier, "defaultResourceSupplier");
            checkNotNull(deserializer, "deserializer");
            checkNotNull(curatorFactory, "curatorFactory");
        }

        public ZkBasedNodeResource<E> build() {
            ensure();
            return new ZkBasedNodeResource<>(this);
        }
    }
}
