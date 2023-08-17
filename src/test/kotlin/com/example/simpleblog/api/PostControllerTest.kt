package com.example.simpleblog.api

import com.example.simpleblog.core.base.BaseRestControllerTest
import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.domain.member.MemberSaveReq
import com.example.simpleblog.domain.member.Role
import com.example.simpleblog.domain.post.PostRes
import com.example.simpleblog.domain.post.PostSaveReq
import com.example.simpleblog.util.JsonUtils
import com.example.simpleblog.util.value.CommonResDto
import com.fasterxml.jackson.core.type.TypeReference
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/**
 * Created by mskwon on 2023/07/23.
 */
class PostControllerTest : BaseRestControllerTest() {

    @Test
    fun posts() {
        val result = mockMvc.get("/posts") {
            param("page","0")
            param("size","10")
        }
            .andExpect {
                status { isOk() }
            }
            /*.andDo {
                print()
            }*/
            .andReturn()
        val content = result.response.contentAsString
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<Page<PostRes>>>() {})
        response.data.content.forEach { println(JsonUtils.toMapperPrettyJson(it)) }
    }

    @Test
    fun findById() {
        val result = mockMvc.get("/post/{id}",1) {}
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
            .andReturn()
        val content = result.response.contentAsString
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<PostRes>>() {})
        println(JsonUtils.toMapperPrettyJson(response))
    }

    @Test
    fun deleteById() {
        val result = mockMvc.delete("/post/{id}",1) {}
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
            .andReturn()
        val content = result.response.contentAsString
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<Unit>>() {})
        println(JsonUtils.toMapperPrettyJson(response))
    }

    @Test
    fun save() {
        val result = mockMvc.post("/post") {
            jsonContent(
                PostSaveReq(
                    title = "ok", content = "ok content", memberId = 1
                )
            )
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
            .andReturn()
        val content = result.response.contentAsString
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<PostRes>>() {})
        println(JsonUtils.toMapperPrettyJson(response))
    }
}