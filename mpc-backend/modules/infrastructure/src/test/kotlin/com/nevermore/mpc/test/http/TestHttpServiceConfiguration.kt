package com.nevermore.mpc.test.http

import com.nevermore.mpc.infra.http.DynamicHttpServiceFactory
import com.nevermore.mpc.infra.http.HttpServiceFactory
import org.apache.curator.framework.CuratorFramework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

/**
 * @author nevermore
 * @since
 */
@Configuration
class TestHttpServiceConfiguration(
    @Autowired private val zookeeperClient: CuratorFramework
) {

    @Lazy
    @Bean
    fun testHttpService() =
        HttpServiceFactory("www.baidu.com").createService(TestHttpService::class.java)

    @Lazy
    @Bean
    fun testDynamicHttpService() =
        DynamicHttpServiceFactory(
            "/mpc/test/dynamic-http-service",
            zookeeperClient
        ).createService(TestDynamicHttpService::class.java)
}
