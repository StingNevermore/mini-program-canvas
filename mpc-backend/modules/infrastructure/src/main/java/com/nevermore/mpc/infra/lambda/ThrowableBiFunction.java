package com.nevermore.mpc.infra.lambda;

/**
 * @author nevermore
 * @since 0.0.1
 */
public interface ThrowableBiFunction<T, U, R, X extends Throwable> {

    R apply(T t, U u) throws X;
}
