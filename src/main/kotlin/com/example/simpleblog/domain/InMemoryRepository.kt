package com.example.simpleblog.domain

import java.util.*

interface InMemoryRepository {

    fun save(key: String, value: Any)

    fun findByKey(key: String): Any

    fun findAll(): List<Any>

    fun remove(key: String): Any?

    fun clear()
}