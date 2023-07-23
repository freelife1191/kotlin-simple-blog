package com.example.simpleblog.domain.member

import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

/**
 * Created by mskwon on 2023/07/03.
 */
interface MemberRepository: JpaRepository<Member, Long>, MemberCustomRepository {
}

interface MemberCustomRepository {
    fun findMembers(pageable: Pageable): Page<Member>
}

/**
 * https://github.com/line/kotlin-jdsl/blob/main/spring/README.md#quick-start---jpa-30
 * @property queryFactory SpringDataQueryFactory
 * @constructor
 */
class MemberCustomRepositoryImpl (
    private val queryFactory: SpringDataQueryFactory,
): MemberCustomRepository {
    val log = KotlinLogging.logger {  }
    override fun findMembers(pageable: Pageable): Page<Member> {
        val results = queryFactory.listQuery<Member> {
            select(entity(Member::class))
            from(entity(Member::class))
            limit(pageable.pageSize)
            offset(pageable.offset.toInt())
            orderBy(ExpressionOrderSpec(column(Member::id), false))
        }

        val countQuery = queryFactory.listQuery<Member> {
            select(entity(Member::class))
            from(entity(Member::class))
        }

        return PageableExecutionUtils.getPage(results, pageable) {
            countQuery.size.toLong();
        }
    }

}