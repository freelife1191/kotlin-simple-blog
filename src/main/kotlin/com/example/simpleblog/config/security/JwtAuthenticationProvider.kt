package com.example.simpleblog.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import mu.KotlinLogging
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by mskwon on 2024/01/13.
 */
class JwtAuthenticationProvider(
    val accessTokenExpireSecond: Long = 300, // 5분
    val refreshTokenExpireDay: Long = 7 // 7일
) {
    private val log = KotlinLogging.logger {  }
    private val accessSecretKey: String = "accessSimpleblog"
    private val refreshSecretKey: String = "refreshSimpleblog"
    private val claimEmail = "email"
    val claimPrincipal = "principal"
    private val jwtSubject = "my-token"

    fun generateRefreshToken(principal: String): String {
        val expireDate = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(refreshTokenExpireDay))
        log.info { "refreshToken ExpireDate=>$expireDate" }
        return doGenerateToken(expireDate, principal, refreshSecretKey)
    }

    fun generateAccessToken(principal: String): String {
        val expireDate = Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(accessTokenExpireSecond))
        log.info { "accessToken ExpireDate=>$expireDate" }
        return doGenerateToken(expireDate, principal, accessSecretKey)
    }

    private fun doGenerateToken(expireDate: Date, principal: String, secretKey: String) =
        JWT.create()
            .withSubject(jwtSubject)
            .withExpiresAt(expireDate)
            .withClaim(claimPrincipal, principal)
            .sign(Algorithm.HMAC512(secretKey))

    fun getPrincipalStringByAccessToken(accessToken: String): String {
        val decodedJWT = getDecodedJwt(secretKey = accessSecretKey, token = accessToken)
        return decodedJWT.getClaim(claimPrincipal).asString()
    }

    fun getPrincipalStringByRefreshToken(refreshToken: String): String {
        val decodedJWT = getDecodedJwt(secretKey = refreshSecretKey, token = refreshToken)
        return decodedJWT.getClaim(claimPrincipal).asString()
    }

    private fun getDecodedJwt(secretKey: String, token: String): DecodedJWT {
        val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(secretKey)) // specify an specific claim validations
            .build()
        return verifier.verify(token)
    }

    fun validAccessToken(token: String): TokenValidResult {
        return validatedJwt(token, accessSecretKey)
    }

    fun validRefreshToken(token: String): TokenValidResult {
        return validatedJwt(token, refreshSecretKey)
    }

    fun validatedJwt(token: String, secretKey: String): TokenValidResult { // TRUE | JWTVerificationException
        try {
            getDecodedJwt(secretKey , token)
            return TokenValidResult.Success()
        } catch (e: JWTVerificationException) {
            // Invalid signature/claims
            // log.error { "error=>${e.stackTraceToString()}" }
            return TokenValidResult.Failure(e)
        }
    }
}

/**
 * 코틀린으로 Union Type 같이 흉내
 */
sealed class TokenValidResult {
    class Success(val successValue: Boolean = true) : TokenValidResult()
    class Failure(val exception: JWTVerificationException) : TokenValidResult()
}