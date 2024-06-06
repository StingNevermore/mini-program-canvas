package com.nevermore.mpc.web.vos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * @author nevermore
 * @since
 */
data class UserRegisterRequestVo(
    @field:NotBlank(message = "{web.param.check.message.userName.NotBlank}")
    val userName: String?,
    @field:NotNull
    val password: String?,
)

data class UserRegisterResponseVo(
    val userId: Long,
)
