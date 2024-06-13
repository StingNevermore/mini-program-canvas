package com.nevermore.mpc.web.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author nevermore
 * @since
 */
@RestController
@RequestMapping("/operation/t")
class TenantController {

    @PostMapping("/register")
    fun registerTenant() {

    }
}
