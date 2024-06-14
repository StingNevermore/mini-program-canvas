package com.nevermore.mpc.web.controllers

import com.nevermore.mpc.components.service.TenantService
import com.nevermore.mpc.vos.ResponseUtils.success
import com.nevermore.mpc.vos.ResponseVoWithData
import com.nevermore.mpc.web.vos.TenantRegisterRequest
import com.nevermore.mpc.web.vos.TenantRegisterResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author nevermore
 * @since
 */
@RestController
@RequestMapping("/operation/t")
class TenantController(val tenantService: TenantService) {

    @PostMapping("/register")
    fun registerTenant(@Valid @RequestBody tenantRegisterRequest: TenantRegisterRequest):
            ResponseVoWithData<TenantRegisterResponse> {
        val tenantId = tenantService.createTenant(tenantRegisterRequest.tenantName!!)
        return success(TenantRegisterResponse(tenantId))
    }
}
