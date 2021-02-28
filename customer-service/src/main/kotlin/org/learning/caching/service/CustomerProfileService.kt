package org.learning.caching.service

import org.learning.caching.config.CustomerServiceConfig
import org.learning.caching.domain.CustomerProfile
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class CustomerProfileService(
    customerServiceConfig: CustomerServiceConfig
) {
    private val delayRange = customerServiceConfig.minDelay..customerServiceConfig.maxDelay
    private val customers = mapOf(
        "c123" to CustomerProfile(customerId = "c123", name = "Ashish", address = "Ping Pong"),
        "c124" to CustomerProfile(customerId = "c124", name = "Kumar", address = "Ping Pong Ping"),
        "c125" to CustomerProfile(customerId = "c125", name = "Joy", address = "Ping Pong Ping Ding dong ding"),
    )

    fun getByCustomerId(customerId: String): Mono<CustomerProfile> {
        return Mono.justOrEmpty(customers[customerId])
            .delayElement(getDelay())
    }

    private fun getDelay(): Duration {
        return Duration.ofMillis(delayRange.random().toLong())
    }
}
