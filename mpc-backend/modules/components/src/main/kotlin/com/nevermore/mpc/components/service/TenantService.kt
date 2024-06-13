package com.nevermore.mpc.components.service

import com.nevermore.mpc.infra.utils.UniqueIdUtils.generateUniqueId
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

/**
 * @author nevermore
 * @since
 */
interface TenantService {
    fun createTenant(): String
}

@Lazy
@Service
class TenantServiceImpl : TenantService {

    override fun createTenant(): String {
        val tenantId = generateUniqueId()

        return tenantId
    }

}
