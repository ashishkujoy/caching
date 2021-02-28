package org.learning.caching.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "services.customer-service")
data class CustomerServiceConfig(val baseUrl: String, val profilePathUrl: String)