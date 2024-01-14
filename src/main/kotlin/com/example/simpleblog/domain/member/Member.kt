package com.example.simpleblog.domain.member

import com.example.simpleblog.domain.BaseEntity
import jakarta.persistence.*

/**
 * Created by mskwon on 2023/07/02.
 */
@Entity
@Table(name = "member")
class Member (
    override var id: Long?,
    @Column(name = "email", nullable = false)
    var email: String,
    @Column(name = "password")
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER
): BaseEntity() {
    override fun toString(): String {
        return "Member(id=$id, email='$email', password='$password', role=$role)"
    }

    companion object {
        fun createFakeMember(memberId: Long): Member =
            Member(id = memberId, email = "admin@gmail.com", password = "1234")
    }
}

fun Member.toDto(): MemberRes {
    return MemberRes(
        id = this.id!!,
        email = this.email,
        password = this.password,
        role = this.role
    )
}

enum class Role {
    USER, ADMIN
}