package com.example.simpleblog

import com.example.simpleblog.domain.comment.CommentSaveReq
import com.example.simpleblog.service.CommentService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class SimpleBlogApplicationTests(
    @Autowired
    val df: DefaultListableBeanFactory
) {

    @Autowired
    private lateinit var commentService: CommentService

    @Test
    fun contextLoads() {
    }

    @Test
    fun saveCommentTest() {
        val dto = CommentSaveReq(
            memberId = 2,
            content = "test content",
            postId = 1,
            idAncestor = 3
        )
        commentService.saveComment(dto)
    }

    @Test
    fun printBeanNames() {
        val names = df.beanDefinitionNames
        for (name in names) {
            val bean = df.getBean(name)
            println("name = $name object = $bean")
        }
    }
}
