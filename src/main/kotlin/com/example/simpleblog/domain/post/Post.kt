package com.example.simpleblog.domain.post

import com.example.simpleblog.domain.BaseEntity
import com.example.simpleblog.domain.member.Member
import jakarta.persistence.*
import lombok.ToString

/**
 * Created by mskwon on 2023/07/02.
 */
@Entity
@ToString
@Table(name = "post")
class Post (
    override var id: Long = 0,
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", length=2000)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member

): BaseEntity(id) {
    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', member=$member)"
    }

    fun toDto(): PostRes {
        val dto = PostRes(
            title = this.title,
            content = this.content,
            member = this.member.toDto()
        )
        setBaseDtoProperty(dto)
        return dto
    }
}