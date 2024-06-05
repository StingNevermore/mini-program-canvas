package com.nevermore.mpc.infra.configuration

import org.apache.curator.RetryPolicy
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import java.util.concurrent.TimeUnit.SECONDS

private const val ZK_RETRY_SLEEP_BASE_TIME_MILLS = 100
private const val ZK_RETRY_MAX_TIMES = 3

/**
 * @author nevermore
 * @since
 */
@Configuration
@ConditionalOnClass(CuratorFramework::class, CuratorFrameworkFactory::class)
@EnableConfigurationProperties(ZookeeperProperties::class)
class ZookeeperAutoConfiguration(val properties: ZookeeperProperties) {

    @Lazy
    @Bean
    fun zookeeperClient(): CuratorFramework =
        CuratorFrameworkFactory.builder()
            .connectString(properties.connectString)
            .retryPolicy(ExponentialBackoffRetry(ZK_RETRY_SLEEP_BASE_TIME_MILLS, ZK_RETRY_MAX_TIMES))
            .connectionTimeoutMs(properties.connectionTimeoutMs)
            .sessionTimeoutMs(properties.sessionTimeoutMs)
            .build()
            .also { it.start() }
}

private val DEFAULT_TIMEOUT_MILLS = SECONDS.toMillis(45).toInt()

@ConfigurationProperties(prefix = "mpc.zookeeper")
data class ZookeeperProperties(
    val connectString: String,
    val connectionTimeoutMs: Int = DEFAULT_TIMEOUT_MILLS,
    val sessionTimeoutMs: Int = DEFAULT_TIMEOUT_MILLS,
    val retryPolicy: Class<out RetryPolicy> = ExponentialBackoffRetry::class.java
)
