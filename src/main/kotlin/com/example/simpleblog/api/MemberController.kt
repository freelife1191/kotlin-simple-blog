package com.example.simpleblog.api

import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by mskwon on 2023/07/03.
 */
@RestController
class MemberController(
        private val memberService: MemberService
) {
    @GetMapping("/members")
    fun findAll(): MutableList<Member> {
        return memberService.findAll()
    }
}