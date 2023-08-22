package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.domain.post.Post
import com.example.simpleblog.domain.post.PostRes

/**
 * Created by mskwon on 2023/08/17.
 */
data class CommentSaveReq(
    val memberId: Long,
    val content: String,
    val postId: Long
)

fun CommentSaveReq.toEntity(post: Post): Comment {
    return Comment(
        content = this.content,
        post = post,
        member = Member.createFakeMember(this.memberId)
    )
}

data class CommentRes(
    val id: Long,
    val member: MemberRes,
    val content: String,
    val post: PostRes
)