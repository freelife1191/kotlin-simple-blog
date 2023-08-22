package com.example.simpleblog.config.aop

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * 전통적인 방식의 프록시 기반 스프링 AOP
 * Created by mskwon on 2023/08/22.
 */
@Component
@Aspect
class LoggerAspect {
    val log = KotlinLogging.logger {  }

    @Pointcut("execution(* com.example.simpleblog.api.*Controller.*(..))")
    private fun controllerCut() = Unit

    @Before("controllerCut()")
    fun controllerLoggerAdvice(jointPoint: JoinPoint) {
        val typeName = jointPoint.signature.declaringTypeName
        val methodName = jointPoint.signature.name
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        log.info { """
            
            request url: ${request.servletPath}
            type: $typeName
            method: $methodName
            
        """.trimIndent() }
    }

    @AfterReturning("controllerCut()", returning = "result")
    fun controllerLogAfter(jointPoint: JoinPoint, result:Any) {
        val mapper = ObjectMapper()

        log.info { """
            
            ${jointPoint.signature.name}
            Method return value: $result}
            
        """.trimIndent() }
    }
}