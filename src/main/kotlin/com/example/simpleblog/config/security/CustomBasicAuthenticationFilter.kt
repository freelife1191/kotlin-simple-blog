package com.example.simpleblog.config.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.simpleblog.domain.InMemoryRepository
import com.example.simpleblog.util.CookieProvider
import com.example.simpleblog.util.JsonUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

/**
 * Created by mskwon on 2024/01/14.
 */
class CustomBasicAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val memoryRepository: InMemoryRepository
): BasicAuthenticationFilter(authenticationManager) {

    val log = KotlinLogging.logger {  }
    private val jwtAuthenticationProvider = JwtAuthenticationProvider()


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        log.info { "권한이나 인증이 필요한 요청이 들어옴" }
        val accessToken = request.getHeader(AUTHORIZATION)?.replace("Bearer ", "")
        if (accessToken == null) {
            log.info { "token이 없음" }
            chain.doFilter(request, response)
            return
        }
        log.debug { "access token: $accessToken" }

        val accessTokenResult: TokenValidResult = jwtAuthenticationProvider.validAccessToken(accessToken)
        if (accessTokenResult is TokenValidResult.Failure) {
            handleTokenException(accessTokenResult) {
                log.info { "getClass==>${accessTokenResult.exception.javaClass}" }
                val refreshToken = CookieProvider.getCookie(request, CookieProvider.CookieName.REFRESH_COOKIE).orElseThrow()
                val refreshTokenResult = jwtAuthenticationProvider.validRefreshToken(refreshToken)
                if (refreshTokenResult is TokenValidResult.Failure) {
                    throw RuntimeException("Invalid RefreshToken")
                }
                // val principalString = jwtAuthenticationProvider.getPrincipalStringByAccessToken(refreshToken)
                // val details = JsonUtils.toMapperObject(principalString, PrincipalDetails::class)
                val details = memoryRepository.findByKey(refreshToken) as PrincipalDetails
                reissueAccessToken(details, response)
                setAuthentication(details, chain, request, response)
            }
            return
        }
        val principalJsonData = jwtAuthenticationProvider.getPrincipalStringByAccessToken(accessToken)
        val principalDetails = JsonUtils.toMapperObject(principalJsonData, PrincipalDetails::class)
        setAuthentication(principalDetails, chain, request, response)
    }

    private fun setAuthentication(
        details: PrincipalDetails,
        chain: FilterChain,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val authentication: Authentication = UsernamePasswordAuthenticationToken(
            details,
            details.password,
            details.authorities
        )

        // Authentication 객체가 인증이 통과했다는 것을 보장
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun reissueAccessToken(
        details: PrincipalDetails,
        response: HttpServletResponse
    ) {
        log.info { "accessToken 재발급" }

        val accessToken = jwtAuthenticationProvider.generateAccessToken(JsonUtils.toMapperJson(details))
        response.addHeader(AUTHORIZATION, "Bearer $accessToken")
    }

    private fun handleTokenException(tokenValidResult: TokenValidResult.Failure, func: () -> Unit) {
        when (tokenValidResult.exception) {
            is TokenExpiredException -> func()
            else -> {
                log.error { tokenValidResult.exception.stackTraceToString() }
                throw tokenValidResult.exception
            }
        }
    }
}