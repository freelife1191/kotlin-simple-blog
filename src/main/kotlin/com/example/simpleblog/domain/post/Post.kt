package com.example.simpleblog.domain.post

import com.example.simpleblog.domain.BaseEntity
import jakarta.persistence.*

/**
 * Created by mskwon on 2023/07/02.
 */
@Entity
@Table(name = "Post")
class Post (
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content")
    var content: String

): BaseEntity()