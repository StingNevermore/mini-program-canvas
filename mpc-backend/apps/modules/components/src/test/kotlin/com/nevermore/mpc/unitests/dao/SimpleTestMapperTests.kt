package com.nevermore.mpc.unitests.dao

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest
import com.nevermore.mpc.dao.SimpleTestDAO

/**
 * @author nevermore
 * @since
 */
@MybatisPlusTest
class SimpleTestMapperTests(
    @Autowired private val simpleTestDAO: SimpleTestDAO
) {

    @Test
    fun testSelect() {
        simpleTestDAO.select()
    }
}
