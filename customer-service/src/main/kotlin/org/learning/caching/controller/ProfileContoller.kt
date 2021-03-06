package org.learning.caching.controller

import org.learning.caching.domain.CustomerProfile
import org.learning.caching.service.CustomerProfileService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ProfileController(
    private val customerProfileService: CustomerProfileService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/customers/{customerId}/profile")
    fun getByCustomerId(@PathVariable customerId: String): Mono<CustomerProfile> {
        return customerProfileService
            .getByCustomerId(customerId)
            .doOnSuccess {
                logger.info("Successfully sending profile for customerId: $customerId")
            }
            .doOnError {
                logger.error("Failure in sending profile for customerId: $customerId")
            }
    }
}