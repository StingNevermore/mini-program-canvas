package com.nevermore.mpc.components.service

import com.nevermore.mpc.components.dao.TenantDAO
import com.nevermore.mpc.components.models.Tenant
import com.nevermore.mpc.infra.utils.UniqueIdUtils.generateUniqueId
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

/**
 * @author nevermore
 * @since
 */
const val LOCKED: Int = 1

interface TenantService {

    fun createTenant(tenantName: String): String

    fun getTenantById(tenantId: String): Tenant? = getTenants(listOf(tenantId))[tenantId]


    fun getTenants(tenantIds: Collection<String>): Map<String, Tenant>

    fun lockTenant(tenantId: String)
}

@Lazy
@Service
class TenantServiceImpl(val tenantDAO: TenantDAO) : TenantService {

    override fun createTenant(tenantName: String): String {
        val tenantId = generateUniqueId()
        tenantDAO.insert(tenantId, tenantName)
        return tenantId
    }

    override fun getTenants(tenantIds: Collection<String>): Map<String, Tenant> =
        tenantDAO.select(tenantIds).associateBy { it.tenantId }

    override fun lockTenant(tenantId: String) {
        tenantDAO.update(tenantId, LOCKED)
    }
}
