package com.example.simpleblog.util

import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.http.ResponseCookie
import java.util.*

/**
 * Created by mskwon on 2024/01/15.
 */
object CookieProvider {

    private val log = KotlinLogging.logger {  }

    fun createNullCookie(cookieName: String): String {
        TODO()
    }

    fun createCookie(cookieName: CookieName, value: String, maxAge: Long): ResponseCookie {
        return ResponseCookie.from(cookieName.name, value)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(maxAge)
            .build()
    }

    fun getCookie(req: HttpServletRequest, cookieName: CookieName): Optional<String> {
        val cookieValue = req.cookies.filter { cookie ->
            cookie.name == cookieName.name
        }.map { cookie ->
            cookie.value
        }.firstOrNull()

        log.info { "cookieValue: $cookieValue" }
        return Optional.ofNullable(cookieValue)
    }

    enum class CookieName {
        REFRESH_COOKIE
    }
}