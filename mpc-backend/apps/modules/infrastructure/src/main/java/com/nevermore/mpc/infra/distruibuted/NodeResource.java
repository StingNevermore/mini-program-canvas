package com.nevermore.mpc.infra.distruibuted;

import java.io.Closeable;

/**
 * @author nevermore
 * @since 0.0.1
 */
public interface NodeResource<T> extends Closeable {
    T getValue();
}
