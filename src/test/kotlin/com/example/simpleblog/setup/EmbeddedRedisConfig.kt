package com.example.simpleblog.setup

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer
import java.io.IOException

/**
 * Created by mskwon on 2024/01/21.
 */
// @TestConfiguration
class EmbeddedRedisConfig {
    private val redisServer = RedisServer(63790)

    @PostConstruct
    @Throws(IOException::class)
    fun start() {
        redisServer.start()
    }

    @PreDestroy
    @Throws(IOException::class)
    fun stop() {
        redisServer.stop()
    }
}
