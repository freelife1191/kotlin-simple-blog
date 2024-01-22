package com.example.simpleblog.domain

import com.example.simpleblog.config.P6spyPrettySqlFormatter
import com.example.simpleblog.domain.post.PostRepository
import com.example.simpleblog.util.dto.SearchCondition
import com.example.simpleblog.util.dto.SearchType
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl
import com.p6spy.engine.spy.P6SpyOptions
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles

/**
 * Created by mskwon on 2024/01/21.
 */
@ActiveProfiles("test")
// 기본값은 ANY H2가 추가되어있어 기본값으로 테스트가 진행되는데, NONE으로 설정하면 application.yml에 설정된 DB로 테스트가 진행된다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@DataJpaTest
class RepositoriesTest {
    private val log = KotlinLogging.logger {  }

    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun setupTest() {
        log.info { "setUp!!" }
    }

    @Test
    fun jdslDynamicQueryTest() {
        val (pageable, condition) = pageRequestAndSearchConditionPair()
        val posts = postRepository.findPosts(pageable = pageable, searchCondition = condition).content
        for (post in posts) {
            log.info { "post==>$post" }
        }
        Assertions.assertThat(posts.size).isEqualTo(6)
    }

    private fun pageRequestAndSearchConditionPair(): Pair<PageRequest, SearchCondition> {
        val pageable = PageRequest.of(0, 100)
        val condition = SearchCondition(
            searchType = SearchType.TITLE,
            keyword = "King"
        )
        return Pair(pageable, condition)
    }

    @TestConfiguration
    class TestConfig (
        @Autowired
        private val em: EntityManager
    ) {
        @Bean
        fun springDataQueryFactory(): SpringDataQueryFactory {
            P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.name
            return SpringDataQueryFactoryImpl(
                criteriaQueryCreator = CriteriaQueryCreatorImpl(
                    em
                ),
                SubqueryCreatorImpl()
            )
        }
    }
}