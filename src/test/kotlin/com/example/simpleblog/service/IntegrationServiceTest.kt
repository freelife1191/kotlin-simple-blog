package com.example.simpleblog.service

import com.example.simpleblog.domain.comment.CommentRepository
import com.example.simpleblog.domain.comment.CommentSaveReq
import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.post.Post
import com.example.simpleblog.domain.post.PostRepository
import com.example.simpleblog.setup.MockitoHelper
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

/**
 * Created by mskwon on 5/6/24.
 */
// @ExtendWith(MockKExtension::class)
@ExtendWith(MockitoExtension::class)
class IntegrationServiceTest {

    private val log = KotlinLogging.logger { }

    // @MockK // mocking 용 객체, 테스트 런타임시 주입
    @Mock
    private lateinit var commentRepositoryMock: CommentRepository
    // @MockK
    @Mock
    private lateinit var postRepositoryMock: PostRepository
    // @InjectMockKs
    @InjectMocks
    private lateinit var commentServiceMock: CommentService

    @Test
    fun mockDiTest() {
        log.info { """
            ${this.commentServiceMock}
        """.trimIndent() }
    }

    @Test
    fun saveCommentTest() {
        // given
        val dto = CommentSaveReq(
            memberId = 1,
            content = "test content",
            postId = 1,
            idAncestor = null
        )
        val post = Optional.ofNullable(Post(
            id = 1,
            title = "title",
            content = "content",
            member = Member.createFakeMember(1)
        ))
        val expectedPost = post.orElseThrow()
        val comment = dto.toEntity(expectedPost)

        // https://velog.io/@3210439/MockkMockito-Kotlin
        // every { postRepositoryMock.save(any()) } returns expectedPost
        // every { commentRepositoryMock.saveComment(any()) } returns comment
        // every { commentRepositoryMock.saveCommentClosure(0, dto.idAncestor) } returns anyInt()
        // val saveComment = commentServiceMock.saveComment(dto)

        // stub
        Mockito.`when`(postRepositoryMock.findById(dto.postId)).thenReturn(post)
        Mockito.`when`(commentRepositoryMock.saveComment(MockitoHelper.anyObject())).thenReturn(comment)
        Mockito.`when`(commentRepositoryMock.saveCommentClosure(0, dto.idAncestor)).thenReturn(anyInt())
        val saveComment = commentServiceMock.saveComment(dto)

        // then
        Assertions.assertEquals(comment.content, saveComment.content)
        Assertions.assertEquals(comment.member.id, saveComment.member.id)
    }
}