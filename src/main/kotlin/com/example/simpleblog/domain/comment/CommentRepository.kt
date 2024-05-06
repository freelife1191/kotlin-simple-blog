package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.member.Member
import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.util.Assert

/**
 * Created by mskwon on 2023/08/17.
 */
interface CommentRepository {
    fun saveComment(comment: Comment): Comment
    fun saveCommentClosure(idDescendant: Long, idAncestor: Long?): Int
    fun findComments(pageable: Pageable): Page<Comment>
}

@Repository
class CommentRepositoryImpl (
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
): CommentRepository {
    override fun saveComment(comment: Comment): Comment {
        Assert.notNull(comment, "Entity must cannot be null.")
        return if (comment.id == 0L) {
            em.persist(comment)
            comment
        } else {
            em.merge(comment)
        }
    }

    override fun saveCommentClosure(idDescendant: Long, idAncestor: Long?): Int {
        var executeCount = 0
        val sql = """
            INSERT INTO comment_closure
            (id_ancestor, id_descendant, depth, updated_at, created_at)
            VALUES
            ($idAncestor, $idDescendant, 0, now(), now())
        """.trimIndent()
        executeCount += em.createNativeQuery(sql).executeUpdate()
        if (idAncestor != null) {
            executeCount += em.createNativeQuery("""
                INSERT INTO comment_closure
                (id_ancestor, id_descendant, depth, updated_at, created_at)
                SELECT
                    cc.id_ancestor,
                    c.id_descendant,
                    cc.depth + c.depth + 1,
                    c.updated_at,
                    c.created_at
                FROM comment_closure as cc, comment_closure as c
                WHERE c.id_ancestor = $idAncestor AND cc.id_descendant = $idDescendant
            """.trimIndent()).executeUpdate()
        }

        return executeCount
    }

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