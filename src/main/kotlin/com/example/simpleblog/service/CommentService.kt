package com.example.simpleblog.service

import com.example.simpleblog.domain.comment.Comment
import com.example.simpleblog.domain.comment.CommentRepository
import com.example.simpleblog.domain.comment.CommentSaveReq
import com.example.simpleblog.domain.post.PostRepository
import com.example.simpleblog.exception.PostNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by mskwon on 5/6/24.
 */
@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {
    private val log = KotlinLogging.logger {}

    @Transactional
    fun saveComment(dto: CommentSaveReq): Comment {
        val post = postRepository.findById(dto.postId).orElseThrow{ throw PostNotFoundException(dto.postId) }
        val comment: Comment = commentRepository.saveComment(dto.toEntity(post = post))
        commentRepository.saveCommentClosure(comment.id, dto.idAncestor)
        return comment
    }
}