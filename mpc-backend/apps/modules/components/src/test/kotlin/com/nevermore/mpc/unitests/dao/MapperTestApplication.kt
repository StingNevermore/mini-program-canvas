package com.nevermore.mpc.unitests.dao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import com.nevermore.mpc.configuration.MybatisPlusConfiguration

/**
 * @author nevermore
 * @since
 */
@SpringBootApplication(scanBasePackages = ["com.nevermore.mpc"])
@Import(MybatisPlusConfiguration::class)
class MapperTestApplication
