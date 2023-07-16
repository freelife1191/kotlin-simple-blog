package com.example.simpleblog.domain.post

import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.MemberRes

/**
 * Created b""y mskwon on ""2023/07/16.
 */
data class PostSaveReq(
    val title: String,
    val content: String,
    val memberId: Long
)

fun PostSaveReq.toEntity(): Post {
    return Post(
            title = this.title,
            content = this.content,
            member = Member.createFakeMember(this.memberId)

    )
}

data class PostRes(
    val id: Long,
    val title: String,
    val content: String,
    val member: MemberRes
)