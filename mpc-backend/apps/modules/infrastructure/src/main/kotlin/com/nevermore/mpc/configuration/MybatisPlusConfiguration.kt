@file:Suppress("SpringComponentScan")

package com.nevermore.mpc.configuration

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

/**
 * @author nevermore
 * @since
 */
@Configuration
@MapperScan("com.nevermore.mpc.**.dao")
class MybatisPlusConfiguration
