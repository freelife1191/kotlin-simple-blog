package com.example.simpleblog.config

import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.MemberRepository
import com.example.simpleblog.domain.member.Role
import io.github.serpro69.kfaker.faker
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

/**
 * Created by mskwon on 2023/07/15.
 */
@Configuration
class InitData(
        private val memberRepository: MemberRepository
) {
    // https://github.com/serpro69/kotlin-faker
    val faker = faker {  }
    private val log = KotlinLogging.logger { }

    @EventListener(ApplicationReadyEvent::class)
    private fun init() {
        val member = Member(
                email = faker.internet.safeEmail(),
                password = "1234",
                role = Role.USER
        )
        log.info { "insert $member" }
        memberRepository.save(member)
    }
}