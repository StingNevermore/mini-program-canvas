package com.nevermore.mpc.infra.distruibuted;

import java.util.function.Function;

import javax.annotation.concurrent.GuardedBy;

import jakarta.annotation.Nullable;

/**
 * @author nevermore
 * @since 0.0.1
 */
public abstract class AbstractNodeResource<T> implements NodeResource<T> {

    private final Object lock = new Object();

    @GuardedBy("lock")
    private volatile boolean closed = false;

    private volatile T resource;
    private final String path;
    private final T defaultResource;

    private final Function<byte[], T> deserializer;


    protected AbstractNodeResource(String path, T defaultResource, Function<byte[], T> deserializer) {
        this.path = path;
        this.defaultResource = defaultResource;
        this.deserializer = deserializer;
    }

    @Override
    public T getValue() {
        checkClosed();

        if (resource == null) {
            synchronized (lock) {
                if (resource == null) {

                    var bytesResource = doGetResource(path);
                    if (bytesResource == null || bytesResource.length == 0) {
                        return defaultResource;
                    }

                    resource = deserializer.apply(bytesResource);
                }
            }
        }

        return resource;
    }

    @Override
    public void close() {
        closed = true;
    }

    protected abstract @Nullable byte[] doGetResource(String path);


    protected void onResourceChanged(byte[] newResource) {
        this.resource = deserializer.apply(newResource);
    }

    private void checkClosed() {
        if (closed) {
            throw new IllegalStateException("Resource has been closed");
        }
    }
}
