package com.nevermore.mpc.test.dao

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest
import com.nevermore.mpc.infra.configuration.MybatisConfiguration
import com.nevermore.mpc.testfixtures.MariaDBHelper.configureDatasourceProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import kotlin.test.assertEquals

/**
 * @author nevermore
 * @since
 */
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MybatisConfiguration::class)
class MybatisConfigurationTest(
    @Autowired val simpleConfigurationTestDAO: SimpleConfigurationTestDAO
) {

    @Test
    fun testConfiguration() {
        val result = simpleConfigurationTestDAO.select()
        assertEquals(1, result)
    }

    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) = configureDatasourceProperties(registry)
    }
}
