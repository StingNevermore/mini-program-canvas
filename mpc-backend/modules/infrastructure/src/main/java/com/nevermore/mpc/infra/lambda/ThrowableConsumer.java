package com.nevermore.mpc.infra.lambda;

/**
 * @author nevermore
 * @since 0.0.1
 */
public interface ThrowableConsumer<T, X extends Throwable> {
    void accept(T t) throws X;
}
