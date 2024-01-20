package com.example.simpleblog.api

import com.example.simpleblog.domain.member.LoginDto
import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.service.AuthService
import com.example.simpleblog.service.MemberService
import com.example.simpleblog.util.value.CommonResDto
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by mskwon on 2024/01/12.
 */
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    val log = KotlinLogging.logger { }

    @GetMapping("/login")
    fun login(session: HttpSession): CommonResDto<Any> {
        session.setAttribute("principal", "pass")
        return CommonResDto(HttpStatus.OK, "session set OK!", session.getAttribute("principal"))
    }

    @PostMapping("/member")
    fun joinApp(@Valid @RequestBody dto: LoginDto): CommonResDto<MemberRes> =
        CommonResDto(HttpStatus.OK, "회원가입", authService.saveMember(dto))
}