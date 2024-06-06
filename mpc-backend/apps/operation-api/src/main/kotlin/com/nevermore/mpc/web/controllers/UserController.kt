package com.nevermore.mpc.web.controllers

import com.nevermore.mpc.web.vos.UserRegisterRequestVo
import com.nevermore.mpc.web.vos.UserRegisterResponseVo
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


/**
 * @author nevermore
 * @since 0.0.1
 */
@RestController
@RequestMapping("/rest/u")
class UserController {

    @ResponseBody
    @RequestMapping(value = ["/register"], method = [(RequestMethod.POST)])
    fun registerUser(@Valid @RequestBody requestVo: UserRegisterRequestVo): ResponseEntity<Result<UserRegisterResponseVo>> {
        return ResponseEntity.ok(Result.success(UserRegisterResponseVo(1)))
    }
}
