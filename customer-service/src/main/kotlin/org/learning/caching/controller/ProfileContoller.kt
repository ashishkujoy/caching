package org.learning.caching.controller

import org.learning.caching.domain.CustomerProfile
import org.learning.caching.service.CustomerProfileService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
class ProfileController(private val customerProfileService: CustomerProfileService) {

    @GetMapping("/customers/{customerId}/profile")
    fun getByCustomerId(@PathVariable customerId: String): Mono<CustomerProfile> {
        return customerProfileService
            .getByCustomerId(customerId)
            .delayElement(Duration.ofMillis(450))
    }
}