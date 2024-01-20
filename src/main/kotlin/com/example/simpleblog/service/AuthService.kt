package com.example.simpleblog.service

import com.example.simpleblog.config.security.PrincipalDetails
import com.example.simpleblog.domain.member.LoginDto
import com.example.simpleblog.domain.member.MemberRepository
import com.example.simpleblog.domain.member.MemberRes
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by mskwon on 2024/01/13.
 */
@Service
class AuthService(
    private val memberRepository: MemberRepository,
): UserDetailsService {

    val log = KotlinLogging.logger {  }
    override fun loadUserByUsername(email: String): UserDetails {
        log.info { "loadUserByUsername 호출!" }
        val member = memberRepository.findMemberByEmail(email)
        return PrincipalDetails(member)
    }

    @Transactional
    fun saveMember(dto: LoginDto): MemberRes {
        return memberRepository.save(dto.toEntity()).toDto()
    }
}