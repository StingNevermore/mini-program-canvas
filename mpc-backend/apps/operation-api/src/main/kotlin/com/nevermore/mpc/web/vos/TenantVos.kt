package com.nevermore.mpc.web.vos

import jakarta.validation.constraints.NotBlank

/**
 * @author nevermore
 * @since
 */
data class TenantRegisterRequest(
    @NotBlank(message = "{web.param.check.message.tenantName.NotBlank}")
    val tenantName: String?
)

data class TenantRegisterResponse(
    val tenantId: String
)
