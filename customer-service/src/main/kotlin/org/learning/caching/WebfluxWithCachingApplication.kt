package org.learning.caching

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class WebfluxWithCachingApplication

fun main(args: Array<String>) {
	runApplication<WebfluxWithCachingApplication>(*args)
}
