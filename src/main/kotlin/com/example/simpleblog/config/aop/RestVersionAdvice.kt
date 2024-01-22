package com.example.simpleblog.config.aop

import com.example.simpleblog.util.value.CommonResDto
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * Created by mskwon on 2024/01/22.
 */
@RestControllerAdvice
class RestVersionAdvice<T> (

): ResponseBodyAdvice<T> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    /**
     * API 응답시 resultMsg에 API 버전을 추가
     * @param body T?
     * @param returnType MethodParameter
     * @param selectedContentType MediaType
     * @param selectedConverterType Class<out HttpMessageConverter<*>>
     * @param request ServerHttpRequest
     * @param response ServerHttpResponse
     * @return T?
     */
    override fun beforeBodyWrite(
        body: T?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): T? {
        if (body is CommonResDto<*>) {
            val apiVersion = request.uri.path.split("/").first { it.isNotEmpty() }
            body.reflectVersion(apiVersion)
        }
        return body
    }
}