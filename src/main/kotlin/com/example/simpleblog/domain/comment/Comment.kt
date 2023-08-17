package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.BaseEntity
import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.post.Post
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Setter
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

/**
 * Created by mskwon on 2023/07/03.
 */
@Entity
@Setter(AccessLevel.PROTECTED)
@Table(name = "comment")
class Comment (
    @Column(name = "content", nullable = false, length = 1000)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member
): BaseEntity() {
    override fun toString(): String {
        return "Comment(content='$content', post=$post, member=$member)"
    }
}