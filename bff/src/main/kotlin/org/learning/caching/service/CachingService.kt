package org.learning.caching.service

import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.learning.caching.gateway.DemogProfile
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import kotlin.system.measureTimeMillis

@Service
class CachingService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .withCache(
            "customer-profile",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String::class.java,
                DemogProfile::class.java,
                ResourcePoolsBuilder.heap(10)
            )
        )
        .build(true)
    private val cache = cacheManager.getCache(
        "customer-profile",
        String::class.java,
        DemogProfile::class.java,
    )

    fun getDemogProfileFromCache(customerId: String, fallback: () -> Mono<DemogProfile>): Mono<DemogProfile> {
        val cachingEnabled = (System.getenv("CACHING_ENABLED") ?: "false").toBoolean()

        var demogProfile: DemogProfile?
        val timeTakenToGetFromCache = measureTimeMillis {
            demogProfile = cache.get(customerId)
        }

        logger.info("Time taken to get from cache $timeTakenToGetFromCache")
        return if (cachingEnabled) {
            Mono.justOrEmpty(demogProfile)
                .doOnSuccess {
                    logger.info("Serving from cache")
                }
                .switchIfEmpty(fallback().doOnSuccess {
                    val timeTakenToPutInCache = measureTimeMillis {
                        cache.put(customerId, it)
                    }
                    logger.info("Time taken to put in cache: $timeTakenToPutInCache")
                    logger.info("Serving from actual service")
                })
        } else {
            fallback()
                .doOnSuccess {
                    logger.info("Serving from actual service")
                }
        }
    }

}