package com.nevermore.mpc.infra.utils

import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 * @author nevermore
 * @since
 */
object KtLazyHelper {
    val KProperty0<*>.isLazyInitialized: Boolean
        get() {
            // Prevent IllegalAccessException from JVM access check
            isAccessible = true
            return (getDelegate() as Lazy<*>).isInitialized()
        }

    val <T> KProperty0<T>.orNull: T?
        get() = if (isLazyInitialized) get() else null
}
