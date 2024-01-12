package com.example.simpleblog.api

import com.example.simpleblog.util.value.CommonResDto
import jakarta.servlet.http.HttpSession
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by mskwon on 2024/01/12.
 */
@RestController
@RequestMapping("/auth")
class AuthController {
    val log = KotlinLogging.logger { }

    @GetMapping("/login")
    fun login(session: HttpSession): CommonResDto<Any> {
        session.setAttribute("principal", "pass")
        return CommonResDto(HttpStatus.OK, "session set OK!", session.getAttribute("principal"))
    }
}