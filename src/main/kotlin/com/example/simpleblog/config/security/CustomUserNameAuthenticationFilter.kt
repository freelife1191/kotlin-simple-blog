package com.example.simpleblog.config.security

import com.example.simpleblog.domain.member.LoginDto
import com.example.simpleblog.util.JsonUtils
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Created by mskwon on 2024/01/13.
 */
class CustomUserNameAuthenticationFilter(
) : UsernamePasswordAuthenticationFilter() {

    private val log = KotlinLogging.logger {  }
    private val jwtAuthenticationProvider = JwtAuthenticationProvider()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        log.info { "login 요청 옴" }

        lateinit var loginDto: LoginDto
        try {
            loginDto = JsonUtils.toMapperObject(request?.inputStream, LoginDto::class)
            log.info { "login Dto : $loginDto" }
        } catch (e: Exception) {
            log.error { "loginFilter: 로그인 요청 Dto 생성 중 실패! $e" }
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        log.info { "로그인 완료되어서 JWT 토큰 만들어서 response" }
        val principalDetails = authResult?.principal as PrincipalDetails
        val jwtToken = jwtAuthenticationProvider.generateAccessToken(principalDetails)
        response?.addHeader(AUTHORIZATION,  "Bearer " + jwtToken)
    }
}