package com.example.simpleblog.api

import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.service.MemberService
import com.example.simpleblog.util.value.CommonResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
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
    fun findAll(@PageableDefault(size = 10) pageable: Pageable): CommonResDto<Page<MemberRes>> =
        CommonResDto(HttpStatus.OK, "find All Members", memberService.findMembers(pageable))
}