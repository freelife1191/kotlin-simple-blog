package com.example.simpleblog.util

import com.example.simpleblog.domain.HashMapRepositoryImpl
import com.example.simpleblog.domain.InMemoryRepository
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * Created by mskwon on 2024/01/21.
 */
class HashMapTest {

    private val log = KotlinLogging.logger {  }

    @Test
    fun hashMapRepoTest() {
        val repo: InMemoryRepository = HashMapRepositoryImpl()
        val numberOfThreads = 1000

        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfThreads)

        for (index in 1 .. numberOfThreads) {
            service.submit {
                repo.save(index.toString(), index)
                latch.countDown()
            }
        }
        latch.await()
        // Thread.sleep(1000)
        val results = repo.findAll()
        Assertions.assertThat(results.size).isEqualTo(numberOfThreads)
    }

}