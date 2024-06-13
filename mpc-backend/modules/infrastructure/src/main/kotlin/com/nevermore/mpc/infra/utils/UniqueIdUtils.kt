package com.nevermore.mpc.infra.utils

import java.util.*


/**
 * @author nevermore
 * @since
 */
object UniqueIdUtils {

    fun generateUniqueId(): String {
        val nanoTime = System.nanoTime()
        val encodeToString = Base64.getUrlEncoder().encodeToString(nanoTime.toString().reversed().toByteArray())
        return encodeToString.substring(0, 10)
    }
}

