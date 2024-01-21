package com.example.simpleblog.api

import com.example.simpleblog.util.dto.SearchCondition
import com.example.simpleblog.util.dto.SearchType
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by mskwon on 2023/05/08.
 */
@RestController
class TestController {

    private val log = KotlinLogging.logger {  }

    @GetMapping("/health")
    fun healthTest(): String = "hello kotlin-blog"

    // @GetMapping("/error") // 시큐리티 Default error redirect 주소
    // fun errorTest(): String = "error"

    @GetMapping("/enum2")
    fun enumTest2(searchType: SearchType): String {
        return searchType.name
    }

    @GetMapping("/enum")
    fun enumTest(searchCondition: SearchCondition): String {
        log.info { """
            $searchCondition
            
            ${searchCondition.searchType}
            ${searchCondition.keyword}
            
        """.trimIndent() }
        return "test"
    }
}