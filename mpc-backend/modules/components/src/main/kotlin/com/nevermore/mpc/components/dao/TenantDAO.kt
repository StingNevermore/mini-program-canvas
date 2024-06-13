package com.nevermore.mpc.components.dao

import com.nevermore.mpc.components.models.Tenant
import org.springframework.stereotype.Repository

/**
 * @author nevermore
 * @since
 */
@Repository
interface TenantDAO {

    fun insert(tenantId: String, tenantName: String): Int

    fun select(tenantIds: Collection<String>): List<Tenant>
}
