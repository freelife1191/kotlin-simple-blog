package com.example.simpleblog.service

import com.example.simpleblog.domain.member.MemberRepository
import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.domain.member.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by mskwon on 2023/07/03.
 */
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    @Transactional(readOnly = true)
    fun findMembers(pageable: Pageable): Page<MemberRes> =
        memberRepository.findMembers(pageable).map { it.toDto() }
}