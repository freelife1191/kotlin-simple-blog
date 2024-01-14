package com.example.simpleblog.domain.member

import jakarta.validation.constraints.NotNull

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
    val password: String?,
    val role: Role?
)

fun LoginDto.toEntity(): Member {
    return Member(
        id = null,
        email = this.email ?: "",
        password = this.password ?: "",
        role = this.role ?: Role.USER
    )
}

data class MemberRes(
    val id: Long?,
    val email: String?,
    val password: String?,
    val role: Role?
) {
    constructor() : this(null, null, null, null)
}