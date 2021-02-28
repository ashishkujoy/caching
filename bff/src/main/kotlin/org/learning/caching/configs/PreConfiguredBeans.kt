package org.learning.caching.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PreConfiguredBeans {
    @Bean
    fun webClient(): WebClient = WebClient.builder().build()
}