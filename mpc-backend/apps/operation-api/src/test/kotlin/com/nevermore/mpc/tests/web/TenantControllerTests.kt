package com.nevermore.mpc.tests.web

import com.nevermore.mpc.components.service.TenantService
import com.nevermore.mpc.infra.utils.ObjectMapperUtils.toJson
import com.nevermore.mpc.infra.utils.UniqueIdUtils.generateUniqueId
import com.nevermore.mpc.web.controllers.TenantController
import com.nevermore.mpc.web.vos.TenantRegisterRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author nevermore
 * @since
 */
@WebMvcTest(controllers = [TenantController::class])
class TenantControllerTests(
    @Autowired private val mockMvc: MockMvc,
) {

    @MockBean
    private lateinit var tenantService: TenantService

    @Test
    fun testRegisterTenant() {
        val tenantId = generateUniqueId()
        `when`(tenantService.createTenant(tenantId)).thenReturn(tenantId)
        mockMvc.perform(
            post("/operation/t/register")
                .content(toJson(TenantRegisterRequest(tenantId))).contentType(
                    APPLICATION_JSON
                )
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(0))
            .andExpect(jsonPath("$.data.tenantId").value(tenantId))
    }
}
