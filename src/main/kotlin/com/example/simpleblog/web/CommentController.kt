package com.example.simpleblog.web

import com.example.simpleblog.domain.comment.Comment
import com.example.simpleblog.domain.comment.CommentRes
import com.example.simpleblog.domain.comment.CommentSaveReq
import com.example.simpleblog.service.CommentService
import com.example.simpleblog.util.value.CommonResDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by mskwon on 5/6/24.
 */
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping("/comment")
    fun saveComment(dto: CommentSaveReq): CommonResDto<CommentRes> {
        return CommonResDto(HttpStatus.OK, "save comment", commentService.saveComment(dto))
    }

    @GetMapping("/comment/{idAncestor}")
    fun findCommentByAncestorComment(@PathVariable idAncestor: Long): CommonResDto<List<CommentRes>> {
        return CommonResDto(HttpStatus.OK, "find comment By idAncestor", commentService.findCommentByAncestorComment(idAncestor))
    }
}