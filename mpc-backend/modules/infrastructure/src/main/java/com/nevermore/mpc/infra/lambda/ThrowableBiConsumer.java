package com.nevermore.mpc.infra.lambda;

/**
 * @author nevermore
 * @since 0.0.1
 */
public interface ThrowableBiConsumer<T, E, X extends Throwable> {
    void accept(T t, E e) throws X;
}
