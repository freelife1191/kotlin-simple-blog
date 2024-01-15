package com.example.simpleblog.util

import jakarta.servlet.http.Cookie
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

    fun createCookie(cookieName: String, value: String, maxAge: Long): ResponseCookie {
        return ResponseCookie.from(cookieName, value)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(maxAge)
            .build()
    }

    fun getCookie(req: HttpServletRequest, cookieName: String): Optional<String> {
        val cookieValue = req.cookies.filter { cookie ->
            cookie.name == cookieName
        }.map { cookie ->
            cookie.value
        }.firstOrNull()

        log.info { "cookieValue: $cookieValue" }
        return Optional.ofNullable(cookieValue)
    }
}