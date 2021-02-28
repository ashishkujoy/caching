package org.learning.caching.controller

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.learning.caching.domain.CustomerProfile
import org.learning.caching.domain.GitRepository
import org.learning.caching.service.CustomerProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest(
    @Autowired private val webTestClient: WebTestClient
) {

    @MockkBean
    private lateinit var customerProfileService: CustomerProfileService

    @Test
    fun `should give customer of given id`() {
        val expectedCustomerProfile = CustomerProfile(
            customerId = "c123",
            name = "Ashish Ku Joy",
            gitRepositories = listOf(
                GitRepository(
                    name = "learning kotlin",
                    numberOfCommits = 20
                )
            )
        )

        every { customerProfileService.getByCustomerId(any()) } returns Mono.just(expectedCustomerProfile)

        val customerProfile = webTestClient.get()
            .uri("/bff/c123/profile")
            .header("accept", "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerProfile::class.java)
            .returnResult()
            .responseBody

        assertSoftly {
            customerProfile.shouldNotBeNull()
            customerProfile shouldBe expectedCustomerProfile
        }
    }
}