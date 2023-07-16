package com.example.simpleblog.domain.member

import com.example.simpleblog.domain.BaseEntity
import jakarta.persistence.*

/**
 * Created by mskwon on 2023/07/02.
 */
@Entity
@Table(name = "Member")
class Member (
    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password")
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role


): BaseEntity() {
    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role)"
    }
}

enum class Role {
    USER, ADMIN
}