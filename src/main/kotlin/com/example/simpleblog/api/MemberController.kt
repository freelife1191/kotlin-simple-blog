package com.example.simpleblog.api

import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.domain.member.MemberSaveReq
import com.example.simpleblog.service.MemberService
import com.example.simpleblog.util.value.CommonResDto
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
        CommonResDto(OK, "find All Members", memberService.findMembers(pageable))

    @GetMapping("/member/{id}")
    fun findById(@PathVariable id: Long): CommonResDto<MemberRes> =
        CommonResDto(OK, "find Member by id", memberService.findMemberById(id))

    @DeleteMapping("/member/{id}")
    fun deleteById(@PathVariable id: Long): CommonResDto<Unit> =
        CommonResDto(OK, "delete Member by id", memberService.deleteMember(id))

    @PostMapping("/member")
    fun save(@RequestBody @Valid dto: MemberSaveReq): CommonResDto<MemberRes> =
        CommonResDto(OK, "save member", memberService.saveMember(dto))
}