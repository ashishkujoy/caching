package org.learning.caching.controller

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.learning.caching.domain.CustomerProfile
import org.learning.caching.domain.GitRepository
import org.learning.caching.gateway.DemogProfile
import org.learning.caching.mockserver.CustomerServiceServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerIntegrationTest(
    @Autowired private val webTestClient: WebTestClient
) {

    private val customerProfileServer = CustomerServiceServer(8080)

    @BeforeEach
    fun setUp() {
        customerProfileServer.start()
    }

    @AfterEach
    fun tearDown() {
        customerProfileServer.stop()
    }

    @Test
    fun `should give customer of given id`() {
        val expectedCustomerProfile = CustomerProfile(
            customerId = "c123",
            name = "Ashish Ku Joy",
            gitRepositories = listOf(
                GitRepository(name = "hello-world", numberOfCommits = 10),
                GitRepository(name = "bye-world", numberOfCommits = 10),
                GitRepository(name = "foo-bar", numberOfCommits = 100)
            )
        )

        customerProfileServer.okResponseFor("c123", DemogProfile("Ashish Ku Joy"))

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