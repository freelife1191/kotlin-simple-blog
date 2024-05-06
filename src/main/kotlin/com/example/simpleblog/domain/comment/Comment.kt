package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.BaseEntity
import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.post.Post
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Setter
import lombok.ToString

/**
 * Created by mskwon on 2023/07/03.
 */
@Entity
@Setter(AccessLevel.PROTECTED)
@ToString
@Table(name = "comment")
class Comment (
    override var id: Long = 0,
    @Column(name = "content", nullable = false, length = 1000)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member
): BaseEntity(id) {
    override fun toString(): String {
        return "Comment(id=$id, content='$content', post=$post, member=$member)"
    }

    fun toDto(): CommentRes {
        val dto = CommentRes(
            member = this.member.toDto(),
            content = this.content
        )
        setBaseDtoProperty(dto)
        return dto
    }
}