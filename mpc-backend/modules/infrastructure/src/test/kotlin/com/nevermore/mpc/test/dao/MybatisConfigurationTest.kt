package com.nevermore.mpc.test.dao

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest
import com.nevermore.mpc.infra.configuration.MybatisConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

/**
 * @author nevermore
 * @since
 */
@MybatisPlusTest
@Import(MybatisConfiguration::class)
class MybatisConfigurationTest(
    @Autowired val simpleConfigurationTestDAO: SimpleConfigurationTestDAO
) {

    @Test
    fun testConfiguration() {
        simpleConfigurationTestDAO.select()
    }
}
