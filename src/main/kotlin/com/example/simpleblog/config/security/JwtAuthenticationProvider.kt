package com.example.simpleblog.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import mu.KotlinLogging
import java.util.*

/**
 * Created by mskwon on 2024/01/13.
 */
class JwtAuthenticationProvider {
    private val log = KotlinLogging.logger {  }

    fun generateAccessToken(principal: PrincipalDetails): String {
        return JWT.create()
            .withSubject(principal.username)
            .withExpiresAt(Date(System.nanoTime() + 1000 * 60 * 60))
            .withClaim("email", principal.username)
            .withClaim("password", principal.password)
            .sign(Algorithm.HMAC512("simpleblog"))
    }
}