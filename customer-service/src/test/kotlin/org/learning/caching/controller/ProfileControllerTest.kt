package org.learning.caching.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.learning.caching.domain.CustomerProfile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest(
    @Autowired private val webTestClient: WebTestClient
) {

    @Test
    fun `should give customer of given id`() {
        val customerProfile = webTestClient.get()
            .uri("/customers/c123/profile")
            .header("accept", "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerProfile::class.java)
            .returnResult()
            .responseBody

        assert(customerProfile?.customerId == "c123")
    }
}