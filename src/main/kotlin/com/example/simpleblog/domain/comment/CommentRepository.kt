package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.member.Member
import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

/**
 * Created by mskwon on 2023/08/17.
 */
interface CommentRepository: JpaRepository<Comment, Long>, CommentCustomRepository

interface CommentCustomRepository {
    fun findComments(pageable: Pageable): Page<Comment>
}

class CommentCustomRepositoryImpl (
    private val queryFactory: SpringDataQueryFactory,
): CommentCustomRepository {
    override fun findComments(pageable: Pageable): Page<Comment> {
        val results = queryFactory.listQuery<Comment> {
            select(entity(Comment::class))
            from(entity(Comment::class))
            limit(pageable.pageSize)
            offset(pageable.offset.toInt())
            orderBy(ExpressionOrderSpec(column(Comment::id), false))
        }

        val countQuery = queryFactory.listQuery<Comment> {
            select(entity(Comment::class))
            from(entity(Comment::class))
        }

        return PageableExecutionUtils.getPage(results, pageable) {
            countQuery.size.toLong();
        }
    }

}