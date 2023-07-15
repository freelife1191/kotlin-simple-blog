package com.example.simpleblog.service

import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.MemberRepository
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
    fun findAll(): MutableList<Member> = memberRepository.findAll()
}