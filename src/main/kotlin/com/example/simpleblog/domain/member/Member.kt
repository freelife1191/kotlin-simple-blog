package com.example.simpleblog.domain.member

import com.example.simpleblog.domain.BaseEntity
import jakarta.persistence.*

/**
 * Created by mskwon on 2023/07/02.
 */
@Entity
@Table(name = "Member")
class Member (
    @Column(name = "title", nullable = false)
    var email: String,

    @Column(name = "content")
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role
): BaseEntity()

enum class Role {
    USER, ADMIN
}