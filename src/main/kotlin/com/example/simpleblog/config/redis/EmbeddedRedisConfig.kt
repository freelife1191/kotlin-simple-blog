package com.example.simpleblog.config.redis

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer

/**
 * Created by mskwon on 2024/01/21.
 */
@Configuration
class EmbeddedRedisConfig (

) {
    private val log = KotlinLogging.logger {  }
    lateinit var redisServer: RedisServer
    // @Value("\${spring.data.redis.port}")
    val port: Int = 16379

    @PostConstruct
    fun init() {
        log.debug { "Embedded Redis Server Start: $port" }
        this.redisServer = RedisServer(this.port)
        this.redisServer.start()
    }

    @PreDestroy
    fun destroy() {
        log.debug { "Embedded Redis Server Stop: $port" }
        this.redisServer.stop()
    }
}