package com.example.simpleblog.config.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging

/**
 * Created by mskwon on 2024/01/12.
 */
class MyAuthenticationFilter : Filter {

    val log = KotlinLogging.logger { }
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val serverRequest = request as HttpServletRequest
        serverRequest.session.getAttribute("principal") ?: throw RuntimeException("session not found!!")
        chain?.doFilter(request, response)
    }
}