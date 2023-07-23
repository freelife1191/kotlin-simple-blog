package com.example.simpleblog.api

import com.example.simpleblog.domain.post.PostRes
import com.example.simpleblog.service.PostService
import com.example.simpleblog.util.value.CommonResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by mskwon on 2023/07/03.
 */
@RestController
class PostController (
    private val postService: PostService
) {
    @GetMapping("/posts")
    fun findPosts(@PageableDefault(size = 10) pageable: Pageable): CommonResDto<Page<PostRes>> =
        CommonResDto(HttpStatus.OK, "find posts", postService.findPosts(pageable))
}