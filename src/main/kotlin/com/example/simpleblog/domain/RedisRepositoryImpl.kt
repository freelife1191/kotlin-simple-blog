package com.example.simpleblog.domain

import com.example.simpleblog.config.BlogConfig
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.Optional

/**
 * Created by mskwon on 2024/01/21.
 */
@Repository
class RedisRepositoryImpl (
    private val redisTemplate: RedisTemplate<String, Any>,
    private val blogConfig: BlogConfig = BlogConfig()
): InMemoryRepository {

    private val log = KotlinLogging.logger {  }

    override fun save(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value, blogConfig.jwt.refreshTokenExpire)
    }

    override fun findByKey(key: String): Any {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElseThrow()
    }

    override fun findAll(): List<Any> {
        val keys = redisTemplate.keys("*")
        val list = mutableListOf<Any>()
        for (key in keys) {
            val map = mutableMapOf<String, Any>()
            val value = findByKey(key)
            map[key] = value
            list.add(map)
        }
        return list
    }

    override fun remove(key: String): Any? {
        return redisTemplate.delete(key)
    }

    override fun clear() {
        val keys = redisTemplate.keys("*")
        for (key in keys) {
            redisTemplate.delete(key)
        }
    }
}