package com.example.simpleblog.config.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

/**
 * Created by mskwon on 2024/01/12.
 */
@Configuration
class FilterConfig {

    @Bean
    fun mdcLoggingFilter(): FilterRegistrationBean<MDCLoggingFilter> {
        val bean = FilterRegistrationBean(MDCLoggingFilter())
        bean.addUrlPatterns("/*")
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }
}