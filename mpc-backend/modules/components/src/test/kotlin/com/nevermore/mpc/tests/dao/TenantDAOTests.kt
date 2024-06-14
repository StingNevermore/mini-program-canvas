package com.nevermore.mpc.tests.dao

import com.nevermore.mpc.components.dao.TenantDAO
import com.nevermore.mpc.infra.utils.UniqueIdUtils.generateUniqueId
import com.nevermore.mpc.testfixtures.DAOBaseTests
import com.nevermore.mpc.testfixtures.MariaDBHelper.configureDatasourceProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import kotlin.test.assertEquals

/**
 * @author nevermore
 * @since
 */
class TenantDAOTests(@Autowired val tenantDAO: TenantDAO) : DAOBaseTests() {

    @Test
    fun testInsert() {
        assertEquals(1, tenantDAO.insert(generateUniqueId(), "nevermore"))
    }

    @Test
    fun testSelect() {
        val tenantId = generateUniqueId()
        tenantDAO.insert(tenantId, "nevermore")

        val results = tenantDAO.select(listOf(tenantId))
        assertEquals(1, results.size)
        assertEquals(tenantId, results[0].tenantId)
        assertEquals("nevermore", results[0].tenantName)
    }

    @Test
    fun testUpdate() {
        val tenantId = generateUniqueId()
        tenantDAO.insert(tenantId, "nevermore")

        val result = tenantDAO.update(tenantId, 1)
        assertEquals(1, result)
        val selectResult = tenantDAO.select(listOf(tenantId))
        assertEquals(1, selectResult.size)
        assertEquals(1, selectResult[0].locked)
    }

    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = configureDatasourceProperties(registry)
    }
}
