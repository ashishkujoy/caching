package org.learning.caching.service

import org.learning.caching.domain.CustomerProfile
import org.learning.caching.gateway.DemogGateway
import org.learning.caching.gateway.GithubGateway
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CustomerProfileService(
    private val demogGateway: DemogGateway,
    private val githubGateway: GithubGateway
) {
    fun getByCustomerId(customerId: String): Mono<CustomerProfile> {
        val demogResponse = demogGateway.getCustomerProfileFor(customerId)
        val gitRepositoryResponse = githubGateway.getRepositoryFor(customerId).collectList()

        return Mono.zip(demogResponse, gitRepositoryResponse)
            .map {
                CustomerProfile(customerId = customerId, name = it.t1.name, gitRepositories = it.t2.toList())
            }

    }
}
