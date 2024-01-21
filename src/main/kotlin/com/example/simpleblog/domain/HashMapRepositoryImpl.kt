package com.example.simpleblog.domain

import com.example.simpleblog.config.BlogConfig
import mu.KotlinLogging
import net.jodah.expiringmap.ExpirationPolicy
import net.jodah.expiringmap.ExpiringMap
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Created by mskwon on 2024/01/21.
 */
open class HashMapRepositoryImpl (
    private val blogConfig: BlogConfig = BlogConfig()
): InMemoryRepository {
    private val log = KotlinLogging.logger { }
    // private val store = mutableMapOf<String, Any>() //동시성 에러 발생
    // private val store = ConcurrentHashMap<String, Any>() // 동시성 에러 보완
    // RefreshToken을 저장하는데 Expire 시간이 되면 토큰이 만료됨
    private val store: MutableMap<String, Any> = ExpiringMap.builder()
        .expiration(blogConfig.jwt.refreshTokenExpire.toDays(), TimeUnit.DAYS)
        // .expiration(1000, TimeUnit.MILLISECONDS) // 만료 테스트
        .expirationPolicy(ExpirationPolicy.CREATED)
        .expirationListener { key: String, value: Any ->
            log.info { "key: $key, value: $value expired" }
        }
        .maxSize(Integer.MAX_VALUE)
        .build()
    override fun save(key: String, value: Any) {
        Thread.sleep(50)
        store[key] = value
    }

    override fun findByKey(key: String): Any {
        return Optional.ofNullable(store[key]).orElseThrow {
            throw RuntimeException("not found refreshToken")
        }
    }

    override fun findAll(): List<Any> {
        return store.values.toList()
    }

    override fun remove(key: String): Any? {
        return store.remove(key)
    }

    override fun clear() {
        store.clear()
    }
}