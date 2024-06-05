package com.nevermore.mpc.infra.http

import com.nevermore.mpc.infra.utils.ZkBasedNodeResource
import io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS
import org.apache.curator.framework.CuratorFramework
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Supplier

private const val MAX_MEMORY_SIZE = 16 * 1024 * 1024
private const val DEFAULT_CONNECT_TIMEOUT_MILLS = 5000
private const val RESPONSE_TIMEOUT_MILLS: Long = 30 * 1000

/**
 * @author nevermore
 * @since 0.0.1
 */
class HttpServiceFactory(private val baseUrl: String, private val httpClient: HttpClient?) {

    constructor(baseUrl: String) : this(baseUrl, null)

    fun <T> createService(serviceClass: Class<T>): T {
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { it.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE) }
            .build()
        val httpClient = this.httpClient ?: HttpClient.create()
            .option(CONNECT_TIMEOUT_MILLIS, DEFAULT_CONNECT_TIMEOUT_MILLS)
            .responseTimeout(Duration.ofMillis(RESPONSE_TIMEOUT_MILLS))
        val webClient = WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(exchangeStrategies)
            .baseUrl(baseUrl)
            .build()
        val webClientAdapter = WebClientAdapter.create(webClient)
        val httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter)
            .build()
        return httpServiceProxyFactory.createClient(serviceClass)
    }
}

class DynamicHttpServiceFactory(
    configPrefix: String,
    private val httpClient: HttpClient?,
    curatorFramework: CuratorFramework
) {
    constructor(configPrefix: String, curatorFramework: CuratorFramework) : this(configPrefix, null, curatorFramework)

    private val httpServiceFactory: AtomicReference<HttpServiceFactory> = AtomicReference()

    init {
        val nodeResource = ZkBasedNodeResource.newBuilder<String>(configPrefix)
            .withCuratorFactory { curatorFramework }
            .withDeserializer { b, _ -> String(b) }
            .withDefaultResourceSupplier{ "" }
            .withOnResourceChangedListener { _, n -> httpServiceFactory.set(HttpServiceFactory(String(n.data), httpClient)) }
            .build()
        httpServiceFactory.set(HttpServiceFactory(nodeResource.get(), httpClient))
    }

    fun <T> createService(serviceClass: Class<T>): Supplier<T> =
        Supplier {
            httpServiceFactory.get().createService(serviceClass)
        }
}
