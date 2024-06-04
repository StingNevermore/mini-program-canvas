package com.nevermore.mpc.test.http

import com.nevermore.mpc.infra.http.HttpServiceFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

/**
 * @author nevermore
 * @since
 */
@Configuration
class TestHttpServiceConfiguration {

    @Lazy
    @Bean
    fun testHttpService() =
        HttpServiceFactory("www.baidu.com").createService(TestHttpService::class.java)

}
