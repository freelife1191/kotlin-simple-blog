package com.example.simpleblog.api

import com.example.simpleblog.domain.post.PostRes
import com.example.simpleblog.domain.post.PostSaveReq
import com.example.simpleblog.service.PostService
import com.example.simpleblog.util.dto.SearchCondition
import com.example.simpleblog.util.value.CommonResDto
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

/**
 * Created by mskwon on 2023/07/03.
 */
@RestController
class PostController (
    private val postService: PostService
) {
    @GetMapping("/posts")
    fun findPosts(@PageableDefault(size = 10) pageable: Pageable,
                  searchCondition: SearchCondition): CommonResDto<Page<PostRes>> =
        CommonResDto(OK, "find posts", postService.findPosts(pageable, searchCondition))

    @GetMapping("/post/{id}")
    fun findById(@PathVariable id: Long): CommonResDto<PostRes> =
        CommonResDto(OK, "find Post by id", postService.findPostById(id))

    @DeleteMapping("/post/{id}")
    fun deleteById(@PathVariable id: Long): CommonResDto<Unit> =
        CommonResDto(OK, "delete Post by id", postService.deletePost(id))

    @PostMapping("/post")
    fun save(@RequestBody @Valid dto: PostSaveReq): CommonResDto<PostRes> =
        CommonResDto(OK, "save post", postService.savePost(dto))
}