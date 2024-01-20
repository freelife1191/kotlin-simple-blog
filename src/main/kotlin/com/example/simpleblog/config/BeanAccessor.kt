package com.example.simpleblog.config

import mu.KotlinLogging
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

/**
 * Created by mskwon on 2024/01/20.
 */
// 각 Bean으로 설정된 메소드를 호출할 때마다 새로운 인스턴스가 생성되는 것을 방지하기 위해 proxyBeanMethods = true로 설정
// 방지할 필요가 없다면 불필요한 처리이므로 false 처리하여 부하를 줄인다
// https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes
@Configuration(proxyBeanMethods = false)
class BeanAccessor (

): ApplicationContextAware {

    private val log = KotlinLogging.logger {  }

    init {
        log.info { "this BeanAccessor=>$this" }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        BeanAccessor.applicationContext = applicationContext
    }

    companion object {
        private lateinit var applicationContext: ApplicationContext

        fun <T: Any> getBean(type: KClass<T>): T {
            return applicationContext.getBean(type.java)
        }

        fun <T: Any> getBean(name: String, type: KClass<T>): T {
            return applicationContext.getBean(name, type.java)
        }
    }
}