package com.example.simpleblog.util.func

import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.post.Post
import com.example.simpleblog.util.dto.SearchCondition
import com.example.simpleblog.util.dto.SearchType
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import mu.KotlinLogging
import org.slf4j.LoggerFactory


private val log = LoggerFactory.getLogger(object {}::class.java.`package`.name)
// private val log = KotlinLogging.logger {  }
fun <T> SpringDataCriteriaQueryDsl<T>.dynamicQuery(
    searchCondition: SearchCondition,
): PredicateSpec {
    val keyword = searchCondition?.keyword
    log.debug("keyword==>$keyword")
    return when(searchCondition?.searchType) {
        SearchType.CONTENT -> and(keyword?.let { column(Post::content).like("%$keyword") })
        SearchType.EMAIL -> and(keyword?.let { column(Member::email).like("%$keyword") })
        SearchType.TITLE -> and(keyword?.let { column(Post::title).like("%$keyword") })
        else -> {PredicateSpec.empty}
    }
}