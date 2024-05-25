package com.nevermore.mpc.unitests

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest
import com.nevermore.mpc.unitests.data.dao.SimpleConfigurationTestDAO

/**
 * @author nevermore
 * @since
 */
@MybatisPlusTest
class MybatisPlusConfigurationTests {

    @Autowired
    private lateinit var simpleConfigurationTestDAO: SimpleConfigurationTestDAO

    @Test
    fun mybatisPlusConfiguration() {
        assertEquals(1, simpleConfigurationTestDAO.select())
    }
}
