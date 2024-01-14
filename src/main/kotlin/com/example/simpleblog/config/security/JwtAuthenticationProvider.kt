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
    accessTokenExpireSecond: Long = 300
) {
    private val log = KotlinLogging.logger {  }

    private val secretKey: String = "simpleblog"
    private val claimEmail = "email"
    val claimPrincipal = "principal"
    private val accessTokenExpireMinute: Long = accessTokenExpireSecond
    private val jwtSubject = "my-token"

    fun generateAccessToken(principal: String): String {
        val expireDate = Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(accessTokenExpireMinute))
        log.info { "accessToken ExpireDate=>$expireDate" }
        return JWT.create()
            .withSubject(jwtSubject)
            .withExpiresAt(expireDate)
            .withClaim(claimPrincipal, principal)
            .sign(Algorithm.HMAC512(secretKey))
    }

    fun getMemberEmail(token: String): String? {
        return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
            .getClaim(claimEmail).asString()
    }

    fun getPrincipalStringByAccessToken(accessToken: String): String? =
        validatedJwt(accessToken).getClaim(claimPrincipal).asString()

    fun validatedJwt(accessToken: String): DecodedJWT {
        try {
            val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(secretKey)) // specify an specific claim validations
                // .withIssuer("auth0") // reusable verifier instance
                .build()
            return verifier.verify(accessToken)
        } catch (e: JWTVerificationException) {
            // Invalid signature/claims
            log.error { "error=>${e.stackTraceToString()}" }
            throw RuntimeException("Invalid jwt")
        }
    }
}