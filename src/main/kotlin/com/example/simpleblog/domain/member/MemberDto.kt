package com.example.simpleblog.domain.member

import com.example.simpleblog.config.BeanAccessor
import com.example.simpleblog.domain.BaseDto
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

/**
 * Created by mskwon on 2023/07/16.
 * dto <=> entity 간의 맵핑할 때, 크게 스타일이 2개 있는 거 같음
 *
 * 1. 각 dto, entity에 책임 할당
 * 2. entitymapper라는 것을 하나 만들어서 담당하도록 하는 스타일
 */
data class LoginDto(
    @field:NotNull(message = "require email")
    val email: String?,
    val rawPassword: String?,
    val role: Role?
) {
    fun toEntity(): Member {
        return Member(
            email = this.email ?: "",
            password = encodeRawPassword(),
            role = this.role ?: Role.USER
        )
    }

    private fun encodeRawPassword(): String =
        BeanAccessor.getBean(PasswordEncoder::class)
            .encode(this.rawPassword)
}

data class MemberRes(
    val email: String,
    val password: String,
    val role: Role,
): BaseDto()