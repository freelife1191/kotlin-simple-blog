package com.example.simpleblog.config

import com.example.simpleblog.domain.comment.CommentRepository
import com.example.simpleblog.domain.member.*
import com.example.simpleblog.domain.post.Post
import com.example.simpleblog.domain.post.PostRepository
import com.example.simpleblog.domain.post.PostSaveReq
import com.example.simpleblog.domain.post.toEntity
import io.github.serpro69.kfaker.faker
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by mskwon on 2023/07/15.
 */
@Configuration
class InitData(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {
    // https://github.com/serpro69/kotlin-faker
    val faker = faker {  }
    private val log = KotlinLogging.logger { }

    @EventListener(ApplicationReadyEvent::class)
    private fun init() {
        // memberRepository.saveAll(generateMembers(10))
        // postRepository.saveAll(generatePosts(10))
    }

    private fun generateMembers(cnt: Int): MutableList<Member> {
        val members = mutableListOf<Member>()
        for (i in 1 .. cnt) {
            val member = generateMember()
            log.info { "insert $member" }
            members.add(member)
        }
        return members
    }

    private fun generatePosts(cnt: Int): MutableList<Post> {
        val posts = mutableListOf<Post>()
        for (i in 1 .. cnt) {
            for (j in 1..cnt) {
                val post = generatePost(i)
                log.info { "insert $post" }
                posts.add(post)
            }
        }
        return posts
    }

    private fun generateMember(): Member = LoginDto(
            email = faker.internet.safeEmail(),
            rawPassword = "1234",
            role = Role.USER
    ).toEntity()

    private fun generatePost(memberId: Int): Post = PostSaveReq(
            title = faker.theExpanse.ships(),
            content = faker.quote.matz(),
            memberId = memberId.toLong()
            // memberId = (0 .. 100).random().toLong()
    ).toEntity()
}