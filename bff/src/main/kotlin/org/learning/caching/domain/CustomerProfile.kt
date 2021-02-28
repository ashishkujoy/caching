package org.learning.caching.domain

data class CustomerProfile(
    val customerId: String,
    val name: String,
    val gitRepositories: List<GitRepository>
)
