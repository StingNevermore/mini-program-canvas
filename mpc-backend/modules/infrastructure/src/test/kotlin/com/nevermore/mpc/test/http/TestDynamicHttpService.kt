package com.nevermore.mpc.test.http

import org.springframework.http.ResponseEntity
import org.springframework.web.service.annotation.GetExchange

/**
 * @author nevermore
 * @since
 */
interface TestDynamicHttpService {

    @GetExchange("")
    fun get(): ResponseEntity<String>
}
