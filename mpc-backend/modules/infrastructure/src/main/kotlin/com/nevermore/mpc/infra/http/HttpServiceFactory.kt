package com.nevermore.mpc.infra.http

import io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.netty.http.client.HttpClient
import java.time.Duration

private const val MAX_MEMORY_SIZE = 16 * 1024 * 1024
private const val DEFAULT_CONNECT_TIMEOUT_MILLS = 5000
private const val RESPONSE_TIMEOUT_MILLS: Long = 30 * 1000

/**
 * @author nevermore
 * @since
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
