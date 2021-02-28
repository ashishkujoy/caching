package org.learning.caching.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "customer-service")
data class CustomerServiceConfig(val minDelay: Int, val maxDelay: Int)