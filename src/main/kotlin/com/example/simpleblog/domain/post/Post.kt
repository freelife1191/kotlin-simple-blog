package com.example.simpleblog.domain.post

import com.example.simpleblog.domain.BaseEntity
import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.toDto
import jakarta.persistence.*

/**
 * Created by mskwon on 2023/07/02.
 */
@Entity
@Table(name = "post")
class Post (
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", length=2000)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member

): BaseEntity() {
    override fun toString(): String {
        return "Post(title='$title', content='$content', member=$member)"
    }
}

fun Post.toDto(): PostRes {
    return PostRes(
        id = this.id!!,
        title = this.title,
        content = this.content,
        member = this.member.toDto()
    )
}