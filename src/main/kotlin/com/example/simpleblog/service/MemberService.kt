package com.example.simpleblog.service

import com.example.simpleblog.domain.member.*
import com.example.simpleblog.exception.MemberNotFoundException
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
    fun findAll(pageable: Pageable): Page<MemberRes> =
        memberRepository.findMembers(pageable).map { it.toDto() }

    @Transactional
    fun deleteMember(id: Long) {
        return memberRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findMemberById(id: Long): MemberRes {
        return memberRepository.findById(id)
            .orElseThrow {
                throw MemberNotFoundException(id)
            }.toDto()
    }
}