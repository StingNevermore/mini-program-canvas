package com.nevermore.mpc.testfixtures

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest
import com.nevermore.mpc.infra.configuration.MybatisConfiguration
import com.nevermore.mpc.infra.configuration.MybatisPlusFixAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

/**
 * @author nevermore
 * @since
 */
@MybatisPlusTest(
    excludeAutoConfiguration = [MybatisPlusAutoConfiguration::class],
    includeFilters = [
        Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = [MybatisPlusFixAutoConfiguration::class]
        )
    ]
)
@Import(value = [MybatisConfiguration::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DAOBaseTests
