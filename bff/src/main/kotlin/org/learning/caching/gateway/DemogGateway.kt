package org.learning.caching.gateway

import org.learning.caching.configs.DemogServiceConfig
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DemogGateway(
    private val webClient: WebClient,
    demogServiceConfig: DemogServiceConfig
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val customerDemogProfileUrl = "${demogServiceConfig.baseUrl}/${demogServiceConfig.profilePathUrl}"


    fun getCustomerProfileFor(customerId: String): Mono<DemogProfile> {
        return webClient
            .get()
            .uri(
                customerDemogProfileUrl,
                mapOf("customerId" to customerId)
            )
            .header(HttpHeaders.ACCEPT, "application/json")
            .retrieve()
            .bodyToMono(DemogProfile::class.java)
            .doOnSuccess {
                logger.info("Successfully get customer demog info for customer id: $customerId")
            }
            .doOnError() {
                logger.info("Failed to get customer demog info for customer id: $customerId")
            }
    }
}