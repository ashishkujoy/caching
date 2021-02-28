package org.learning.caching.service

import org.learning.caching.domain.CustomerProfile
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CustomerProfileService {
//    private val webClient = WebClient.builder()
//        .baseUrl()
//        .build()

    fun getByCustomerId(customerId: String): Mono<CustomerProfile> {
//        return Mono.justOrEmpty(customers[customerId])
        return Mono.empty()
    }
}
