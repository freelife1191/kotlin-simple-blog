package com.example.simpleblog.config

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

/**
 * Created by mskwon on 2024/01/21.
 */
@ConfigurationProperties(prefix = "blog")
data class BlogConfig(
    val jwt: JwtProperties = JwtProperties()
) {
    private val log = KotlinLogging.logger { }

    data class JwtProperties(
        val accessTokenExpire: Duration = Duration.ofMinutes(5),
        val refreshTokenExpire: Duration = Duration.ofDays(7)
    )

    @PostConstruct
    private fun init() {
        log.debug { "blog-config: $this" }
    }

}