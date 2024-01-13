package com.example.simpleblog.config.filter

import jakarta.servlet.FilterRegistration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by mskwon on 2024/01/12.
 */
@Configuration
class FilterConfig {

    // @Bean
    fun registerMyAuthenticationFilter(): FilterRegistrationBean<MyAuthenticationFilter> {
        val bean = FilterRegistrationBean(MyAuthenticationFilter())
        bean.addUrlPatterns("/api/*")
        bean.order = 0
        return bean
    }
}