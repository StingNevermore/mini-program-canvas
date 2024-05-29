package com.nevermore.mpc.infra.lambda;

/**
 * @author nevermore
 * @since 0.0.1
 */
public interface ThrowableFunction<T, R, X extends Throwable> {
    R apply(T t) throws X;
}
