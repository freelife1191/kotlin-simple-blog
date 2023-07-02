package com.example.simpleblog.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by mskwon on 2023/05/08.
 */
@RestController
class TestController {

    @GetMapping("/health")
    fun healthTest(): String = "hello kotlin-blog"
}