package com.example.simpleblog.domain

import com.example.simpleblog.config.redis.EmbeddedRedisConfig
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * Created by mskwon on 2024/01/21.
 */
// @Import(EmbeddedRedisConfig::class)
@SpringBootTest
class RedisRepositoryImplTest (
) {
    private val log = KotlinLogging.logger {  }

    @Autowired
    private lateinit var redisRepositoryImpl: InMemoryRepository

    @Test
    fun setup() {
        log.info { "test setup==> ${this.redisRepositoryImpl}" }
    }

    @Test
    fun redisRepositoryTest() {
        val numberOfThreads = 1000

        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfThreads)

        for (index in 1 .. numberOfThreads) {
            service.submit {
                this.redisRepositoryImpl.save(index.toString(), index)
                latch.countDown()
            }
        }
        latch.await()
        val value = this.redisRepositoryImpl.findByKey(1.toString())
        println(value)
        val results = this.redisRepositoryImpl.findAll()
        for (result in results) {
            println(result.toString())
        }
        Assertions.assertThat(results.size).isEqualTo(numberOfThreads)
    }
}