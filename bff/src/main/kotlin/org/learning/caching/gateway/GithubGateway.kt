package org.learning.caching.gateway

import org.learning.caching.domain.GitRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class GithubGateway {
    fun getRepositoryFor(customerId: String): Flux<GitRepository> {
        return Flux.just(
            GitRepository(name = "hello-world", numberOfCommits = 10),
            GitRepository(name = "bye-world", numberOfCommits = 10),
            GitRepository(name = "foo-bar", numberOfCommits = 100)
        )
    }
}