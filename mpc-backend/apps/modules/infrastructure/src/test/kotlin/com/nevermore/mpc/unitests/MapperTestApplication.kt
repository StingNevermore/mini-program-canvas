package com.nevermore.mpc.unitests

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import com.nevermore.mpc.configuration.MybatisPlusConfiguration

/**
 * @author nevermore
 * @since
 */
@SpringBootApplication
@Import(MybatisPlusConfiguration::class)
class MapperTestApplication
