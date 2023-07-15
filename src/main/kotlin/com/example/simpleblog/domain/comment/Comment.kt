package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.BaseEntity
import com.example.simpleblog.domain.post.Post
import jakarta.persistence.*

/**
 * Created by mskwon on 2023/07/03.
 */
@Entity
@Table(name = "Comment")
class Comment (
    @Column(name = "content", nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post
): BaseEntity()