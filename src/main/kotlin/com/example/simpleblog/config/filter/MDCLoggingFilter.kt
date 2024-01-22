package com.example.simpleblog.config.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.slf4j.MDC
import java.util.*

/**
 * Created by mskwon on 2024/01/12.
 */
class MDCLoggingFilter : Filter {

    val log = KotlinLogging.logger { }
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val uuid = UUID.randomUUID()
        MDC.put("request_id", uuid.toString())
        chain.doFilter(request, response)
        MDC.clear()
    }
}