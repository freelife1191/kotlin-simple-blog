package com.example.simpleblog.domain.post

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by mskwon on 2023/07/16.
 */
interface PostRepository: JpaRepository<Post, Long> {
}