package org.learning.caching.service

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.learning.caching.domain.CustomerProfile
import org.learning.caching.domain.GitRepository
import org.learning.caching.gateway.DemogGateway
import org.learning.caching.gateway.DemogProfile
import org.learning.caching.gateway.GithubGateway
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class CustomerProfileServiceTest {
    private val demogGateway = mockk<DemogGateway>()
    private val githubGateway = mockk<GithubGateway>()
    private val customerProfileService = CustomerProfileService(
        demogGateway = demogGateway, githubGateway = githubGateway
    )

    @Test
    fun `should give customer profile`() {
        val helloWorldRepository = GitRepository(name = "hello world", numberOfCommits = 10)

        every { demogGateway.getCustomerProfileFor(any()) } returns Mono.just(DemogProfile(name = "Ashish Ku"))
        every { githubGateway.getRepositoryFor(any()) } returns Flux.just(helloWorldRepository)

        val customerProfile = customerProfileService.getByCustomerId("c123").block()

        customerProfile shouldBe CustomerProfile(
            customerId = "c123",
            name = "Ashish Ku",
            gitRepositories = listOf(helloWorldRepository)
        )
    }

    @Test
    fun `should fetch demog profile from demog gateway`() {
        val helloWorldRepository = GitRepository(name = "hello world", numberOfCommits = 10)

        every { demogGateway.getCustomerProfileFor(any()) } returns Mono.just(DemogProfile(name = "Ashish Ku"))
        every { githubGateway.getRepositoryFor(any()) } returns Flux.just(helloWorldRepository)

        customerProfileService.getByCustomerId("c123").block()

        val customerId = slot<String>()

        assertSoftly {
            verify(exactly = 1) {
                demogGateway.getCustomerProfileFor(capture(customerId))
            }
            customerId.captured shouldBe "c123"
        }
    }

    @Test
    fun `should fetch git repositories from github gateway`() {
        val helloWorldRepository = GitRepository(name = "hello world", numberOfCommits = 10)

        every { demogGateway.getCustomerProfileFor(any()) } returns Mono.just(DemogProfile(name = "Ashish Ku"))
        every { githubGateway.getRepositoryFor(any()) } returns Flux.just(helloWorldRepository)

        customerProfileService.getByCustomerId("c123").block()

        val customerId = slot<String>()

        assertSoftly {
            verify(exactly = 1) {
                githubGateway.getRepositoryFor(capture(customerId))
            }
            customerId.captured shouldBe "c123"
        }
    }
}