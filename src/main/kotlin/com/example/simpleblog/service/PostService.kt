package com.example.simpleblog.service

import com.example.simpleblog.domain.post.PostRepository
import com.example.simpleblog.domain.post.PostRes
import com.example.simpleblog.domain.post.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by mskwon on 2023/07/03.
 */
@Service
class PostService(
    private val postRepository: PostRepository
) {
    @Transactional(readOnly = true)
    fun findPosts(pageable: Pageable): Page<PostRes> =
        postRepository.findPosts(pageable).map { it.toDto() }
}