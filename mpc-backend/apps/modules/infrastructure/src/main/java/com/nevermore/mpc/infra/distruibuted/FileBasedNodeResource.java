package com.nevermore.mpc.infra.distruibuted;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.io.Closeables.closeQuietly;

/**
 * @author nevermore
 * @since 0.0.1
 */
public final class FileBasedNodeResource<T> extends AbstractNodeResource<T> {

    private static final Logger logger = LoggerFactory.getLogger(FileBasedNodeResource.class);

    private final String filePath;

    private FileBasedNodeResource(Builder<T> builder) {
        super(builder.path, builder.defaultResource, builder.deserializer);
        this.filePath = builder.filePath;
    }

    @Override
    protected byte @Nullable [] doGetResource(String path) {
        FileInputStream fis = null;
        try {
            File file = ResourceUtils.getFile(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            fis = new FileInputStream(file);
            return fis.readAllBytes();
        } catch (FileNotFoundException e) {
            logger.warn("file not found: {}", filePath);
        } catch (IOException e) {
            logger.warn("error reading file: %s".formatted(filePath), e);
        } finally {
            if (fis != null) {
                closeQuietly(fis);
            }
        }
        return null;
    }

    public static <B> Builder<B> newBuilder(String filePath) {
        return new Builder<>(filePath);
    }

    @SuppressWarnings("unchecked")
    public static class Builder<E> {

        private final String filePath;
        private String path;
        private E defaultResource;
        private Function<byte[], E> deserializer;


        public Builder(String filePath) {
            this.filePath = filePath;
        }

        public <E1> Builder<E1> withPath(String path) {
            Builder<E1> thisBuilder = (Builder<E1>) this;
            thisBuilder.path = path;
            return thisBuilder;
        }

        public <E1> Builder<E1> withDefaultResource(E1 defaultResource) {
            Builder<E1> thisBuilder = (Builder<E1>) this;
            thisBuilder.defaultResource = defaultResource;
            return thisBuilder;
        }

        public <E1> Builder<E1> withDeserializer(Function<byte[], E1> deserializer) {
            Builder<E1> thisBuilder = (Builder<E1>) this;
            thisBuilder.deserializer = deserializer;
            return thisBuilder;
        }

        private void ensure() {
            checkNotNull(filePath, "filePath");
            checkNotNull(path, "path");
            checkNotNull(deserializer, "deserializer");
        }

        public FileBasedNodeResource<E> build() {
            ensure();
            return new FileBasedNodeResource<>(this);
        }
    }
}
