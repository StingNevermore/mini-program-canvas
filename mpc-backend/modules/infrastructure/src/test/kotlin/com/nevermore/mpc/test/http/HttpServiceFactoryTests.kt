package com.nevermore.mpc.test.http

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import kotlin.test.assertEquals

/**
 * @author nevermore
 * @since
 */
@WebFluxTest
@Import(TestHttpServiceConfiguration::class)
class HttpServiceFactoryTests(
    @Autowired private val testHttpService: TestHttpService
) {

    @Test
    fun test() {
        val response = testHttpService.get()
        assertEquals(200, response.statusCode.value())
    }
}
