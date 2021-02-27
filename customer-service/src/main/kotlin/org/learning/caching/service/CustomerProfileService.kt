package org.learning.caching.service

import org.learning.caching.domain.CustomerProfile
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomerProfileService {
    private val customers = mapOf(
        "c123" to CustomerProfile(customerId = "c123", name = "Ashish", address = "Ping Pong"),
        "c124" to CustomerProfile(customerId = "c124", name = "Kumar", address = "Ping Pong Ping"),
        "c125" to CustomerProfile(customerId = "c125", name = "Joy", address = "Ping Pong Ping Ding dong ding"),
    )

    fun getByCustomerId(customerId: String): Mono<CustomerProfile> {
        return Mono.justOrEmpty(customers[customerId])
    }
}
