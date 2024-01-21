package com.example.simpleblog.domain.post

import com.example.simpleblog.util.dto.SearchCondition
import com.example.simpleblog.util.func.dynamicQuery
import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import jakarta.persistence.criteria.JoinType
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

/**
 * Created by mskwon on 2023/07/16.
 */
interface PostRepository: JpaRepository<Post, Long>, PostCustomRepository {
}

interface PostCustomRepository {
    fun findPosts(pageable: Pageable, searchCondition: SearchCondition): Page<Post>
}

class PostCustomRepositoryImpl (
    private val queryFactory: SpringDataQueryFactory
): PostCustomRepository {
    val log = KotlinLogging.logger {  }
    override fun findPosts(pageable: Pageable, searchCondition: SearchCondition): Page<Post> {
        val results = queryFactory.listQuery<Post> {
            select(entity(Post::class))
            from(entity(Post::class))
            where(
                dynamicQuery(searchCondition)
            )
            fetch(Post::member, joinType = JoinType.LEFT) // JPA의 N+1문제 ManyToOne의 경우 fetch Join으로 한번의 SQL요청으로 연관관계 데이터를 조회해옴
            limit(pageable.pageSize)
            offset(pageable.offset.toInt())
            orderBy(ExpressionOrderSpec(column(Post::id), false))
        }

        val countQuery = queryFactory.listQuery<Post> {
            select(entity(Post::class))
            from(entity(Post::class))
        }

        return PageableExecutionUtils.getPage(results, pageable) {
            countQuery.size.toLong();
        }
    }
}