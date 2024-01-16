package com.example.simpleblog.util

import com.example.simpleblog.config.security.JwtAuthenticationProvider
import com.example.simpleblog.config.security.PrincipalDetails
import com.example.simpleblog.domain.member.Member
import mu.KotlinLogging
import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by mskwon on 2024/01/13.
 */
class JwtTest {
    val log = KotlinLogging.logger {  }

    @Test
    fun bcryptEncodeTest() {
        val encoder = BCryptPasswordEncoder()
        val encodePassword = encoder.encode("1234")
        log.info { encodePassword }
    }

    @Test
    fun generateJwtTest() {
        val jwtAuthenticationProvider = JwtAuthenticationProvider(accessTokenExpireSecond = 60)

        val details = PrincipalDetails(Member.createFakeMember(1))
        val accessToken = jwtAuthenticationProvider.generateAccessToken(JsonUtils.toMapperJson(details))
        val decodedJWT = jwtAuthenticationProvider.validatedJwt(accessToken, "accessSimpleblog")
        log.info { "accessToken $accessToken" }
        log.info { "decodedJWT $decodedJWT" }
    }
}