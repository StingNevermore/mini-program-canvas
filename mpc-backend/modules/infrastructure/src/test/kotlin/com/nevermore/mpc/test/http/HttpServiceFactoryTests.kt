package com.nevermore.mpc.test.http

import com.nevermore.mpc.infra.configuration.ZookeeperAutoConfiguration
import com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import java.util.function.Supplier
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author nevermore
 * @since
 */
private const val connectString = "127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183"

@WebFluxTest(properties = ["mpc.zookeeper.connect-string=$connectString"])
@Import(TestHttpServiceConfiguration::class)
@ImportAutoConfiguration(ZookeeperAutoConfiguration::class)
class HttpServiceFactoryTests(
    @Autowired private val testHttpService: TestHttpService,
    @Autowired private val testDynamicHttpService: Supplier<TestDynamicHttpService>
) {

    @Test
    fun testStatic() {
        val response = testHttpService.get()
        assertEquals(200, response.statusCode.value())
    }

    @Test
    fun testDynamic() {
        val baiduResponse = testDynamicHttpService.get().get()
        assertEquals(200, baiduResponse.statusCode.value())
        assertNotNull(baiduResponse.body)
        assertTrue { baiduResponse.body!!.contains("baidu") }

        setAnotherBaseUrl()
        val bingResponse = testDynamicHttpService.get().get()
        assertEquals(200, bingResponse.statusCode.value())
        assertNotNull(bingResponse.body)
        assertTrue { bingResponse.body!!.contains("bing") }
    }

    private fun setAnotherBaseUrl() {
        getCurator().setData().forPath("/mpc/test/dynamic-http-service", "www.bing.com".toByteArray())
        waitZkSync()
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            val ports = connectString.split(",").map { it.split(":")[1] }.map { it.toInt() }
            setupTestingCluster(ports)
            getCurator().create().creatingParentsIfNeeded().forPath("/mpc/test/dynamic-http-service", "www.baidu.com".toByteArray())
            waitZkSync()
        }

        @JvmStatic
        @AfterAll
        fun teardown() {
            tearDownTestingCluster()
        }
    }
}
