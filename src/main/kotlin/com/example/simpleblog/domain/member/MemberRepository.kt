package com.example.simpleblog.domain.member

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by mskwon on 2023/07/03.
 */
interface MemberRepository: JpaRepository<Member, Long> {
}