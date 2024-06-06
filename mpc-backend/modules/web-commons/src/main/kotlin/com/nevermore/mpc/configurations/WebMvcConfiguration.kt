package com.nevermore.mpc.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.util.*

/**
 * @author nevermore
 * @since
 */
@EnableWebMvc
@Configuration
class WebMvcConfiguration : WebMvcConfigurationSupport() {

    @Bean
    fun messageSource() = ReloadableResourceBundleMessageSource().apply {
        addBasenames("classpath:ValidationMessages")
        setDefaultLocale(Locale.CHINESE)
    }
}
