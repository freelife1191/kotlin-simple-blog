package com.example.simpleblog.config.security

import com.auth0.jwt.exceptions.TokenExpiredException
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
    authenticationManager: AuthenticationManager
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
            if (accessTokenResult.exception is TokenExpiredException) {
                log.info { "getClass==>${accessTokenResult.javaClass}" }
            } else {
                log.error { accessTokenResult.exception.stackTraceToString() }
            }
        }

        val principalJsonData = jwtAuthenticationProvider.getPrincipalStringByAccessToken(accessToken)
        val principalDetails = JsonUtils.toMapperObject(principalJsonData, PrincipalDetails::class)
        // val member = memberRepository.findMemberByEmail(principalJsonData)
        // val principalDetails = PrincipalDetails(member)
        val authentication: Authentication = UsernamePasswordAuthenticationToken(
            principalDetails,
            principalDetails.password,
            principalDetails.authorities
        )
        // Authentication 객체가 인증이 통과했다는 것을 보장
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    /*
    private fun reissueAccessToken(
        e: JWTVerificationException,
        req: HttpServletRequest?
    ) {
        if (e is TokenExpiredException) {
            val refreshToken = CookieProvider.getCookie(req!!, "refreshCookie").orElseThrow()
            val validatedJwt = validatedJwt(refreshToken)
            val principalString = getPrincipalStringByAccessToken(refreshToken)
            val principalDetails = JsonUtils.toMapperObject(principalString, PrincipalDetails::class)

            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                principalDetails,
                principalDetails.password,
                principalDetails.authorities
            )

            SecurityContextHolder.getContext().authentication = authentication // 인증 처리 끝
        }
    }
    */
}