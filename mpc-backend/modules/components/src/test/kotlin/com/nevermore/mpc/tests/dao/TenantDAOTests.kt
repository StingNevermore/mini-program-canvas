package com.nevermore.mpc.tests.dao

import com.nevermore.mpc.components.dao.TenantDAO
import com.nevermore.mpc.infra.utils.UniqueIdUtils.generateUniqueId
import com.nevermore.mpc.testfixtures.DAOBaseTests
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
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
}
