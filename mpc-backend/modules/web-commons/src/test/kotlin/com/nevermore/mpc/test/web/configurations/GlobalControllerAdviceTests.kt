package com.nevermore.mpc.test.web.configurations

import com.nevermore.mpc.configurations.GlobalControllerAdvices
import com.nevermore.mpc.infra.utils.ObjectMapperUtils.toJson
import com.nevermore.mpc.vos.ResponseStatus
import com.nevermore.mpc.vos.ResponseStatus.INTERNAL_SERVER_ERROR
import com.nevermore.mpc.vos.ResponseUtils.failure
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author nevermore
 * @since
 */
@WebMvcTest
@Import(value = [GlobalControllerAdvices::class, ExceptionController::class])
class GlobalControllerAdviceTests(@Autowired private val mvc: MockMvc) {

    @Test
    fun testGlobalException() {
        mvc.perform(MockMvcRequestBuilders.get("/exception"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(toJson(failure(INTERNAL_SERVER_ERROR))))
    }

    @Test
    fun testNotFound() {
        mvc.perform(MockMvcRequestBuilders.get("/impossible"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(toJson(failure(ResponseStatus.NOT_FOUND))))
    }
}

@RestController
class ExceptionController {

    @RequestMapping("/exception")
    fun exception(): ResponseEntity<String> {
        throw Exception()
    }
}
