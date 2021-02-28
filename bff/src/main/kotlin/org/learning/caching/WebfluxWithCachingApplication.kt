package org.learning.caching

import org.learning.caching.configs.DemogServiceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DemogServiceConfig::class)
class WebfluxWithCachingApplication

fun main(args: Array<String>) {
	runApplication<WebfluxWithCachingApplication>(*args)
}
