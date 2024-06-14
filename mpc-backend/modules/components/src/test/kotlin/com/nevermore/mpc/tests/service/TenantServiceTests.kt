package com.nevermore.mpc.tests.service

import com.nevermore.mpc.components.dao.TenantDAO
import com.nevermore.mpc.components.models.Tenant
import com.nevermore.mpc.components.service.TenantService
import com.nevermore.mpc.components.service.TenantServiceImpl
import com.nevermore.mpc.infra.utils.UniqueIdUtils.generateUniqueId
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.anyList
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

/**
 * @author nevermore
 * @since
 */
class TenantServiceTests {

    private val tenantDAO: TenantDAO = mock(TenantDAO::class.java)
    private val service: TenantService = TenantServiceImpl(tenantDAO)

    @Test
    fun testCreateTenant() {
        `when`(tenantDAO.insert(anyString(), anyString()))
            .thenReturn(1)
        service.createTenant("nevermore")
    }

    @Test
    fun testGetTenant() {
        val firstId = generateUniqueId()
        val secondId = generateUniqueId()
        `when`(tenantDAO.select(anyList())).thenReturn(
            listOf(
                Tenant(firstId, "first", 0),
                Tenant(secondId, "second", 0),
            )
        )

        val tenants = service.getTenants(listOf(firstId, secondId))
        assertEquals(2, tenants.size)

        val tenantById = service.getTenantById(firstId)
        assertEquals(firstId, tenantById?.tenantId)
    }

    @Test
    fun testLock() {
        val tenantId = generateUniqueId()
        `when`(tenantDAO.update(anyString(), eq(1))).thenReturn(1)
        service.lockTenant(tenantId)
    }
}
