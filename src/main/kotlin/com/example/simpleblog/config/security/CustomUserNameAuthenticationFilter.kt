package com.example.simpleblog.config.security

import com.example.simpleblog.config.BlogConfig
import com.example.simpleblog.domain.InMemoryRepository
import com.example.simpleblog.domain.member.LoginDto
import com.example.simpleblog.util.CookieProvider
import com.example.simpleblog.util.CookieProvider.CookieName
import com.example.simpleblog.util.JsonUtils
import com.example.simpleblog.util.func.responseData
import com.example.simpleblog.util.value.CommonResDto
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpHeaders.SET_COOKIE
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.concurrent.TimeUnit

/**
 * Created by mskwon on 2024/01/13.
 */
class CustomUserNameAuthenticationFilter(
    private val memoryRepository: InMemoryRepository,
    private val blogConfig: BlogConfig = BlogConfig()
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
            log.error { "loginFilter: 로그인 요청 Dto 생성 중 실패! ${e.stackTraceToString()}" }
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.rawPassword)
        return this.authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        log.info { "로그인 완료되어서 JWT 토큰 만들어서 response" }
        val principalDetails = authResult?.principal as PrincipalDetails
        val accessToken = jwtAuthenticationProvider.generateAccessToken(JsonUtils.toMapperJson(principalDetails))
        val refreshToken = jwtAuthenticationProvider.generateRefreshToken(JsonUtils.toMapperJson(principalDetails))

        val refreshCookie = CookieProvider.createCookie(
            CookieName.REFRESH_COOKIE,
            refreshToken,
            blogConfig.jwt.refreshTokenExpire.toSeconds(),
        )
        response?.addHeader(AUTHORIZATION, "Bearer $accessToken")
        // 모든 브라우저에서 거부되지 않도록 sameSite=Strict/Lax/None; Secure로 설정되지 않은 경우 Set-Cookie 속성으로 Header에 쿠키를 추가
        response?.addHeader(SET_COOKIE, refreshCookie.toString())
        memoryRepository.save(refreshToken, JsonUtils.toMapperJson(principalDetails))

        responseData(response, JsonUtils.toMapperJson(CommonResDto(HttpStatus.OK, "login success", principalDetails.member)))
    }
}