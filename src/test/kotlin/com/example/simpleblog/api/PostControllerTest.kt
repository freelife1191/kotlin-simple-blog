package com.example.simpleblog.api

import com.example.simpleblog.core.base.BaseRestControllerTest
import com.example.simpleblog.domain.post.PostRes
import com.example.simpleblog.domain.post.PostSaveReq
import com.example.simpleblog.util.JsonUtils
import com.example.simpleblog.util.value.CommonResDto
import com.fasterxml.jackson.core.type.TypeReference
import io.kotest.matchers.collections.haveSize
import io.kotest.matchers.maps.haveSize
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/**
 * Created by mskwon on 2023/07/23.
 */
class PostControllerTest : BaseRestControllerTest() {

    @Test
    fun posts() {
        val result = mockMvc.get("/v1/posts") {
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
        val result = mockMvc.get("/v1/post/{id}",1) {}
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
        val result = mockMvc.delete("/v1/post/{id}",1) {}
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
        val result = mockMvc.post("/v1/post") {
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

    @Test
    fun saveError() {
        mockMvc.post("/v1/post") {
            jsonContent(
                PostSaveReq(
                    title = null,
                    content = "content",
                    memberId = 1
                )
            )
        }
        .andExpect {
            status { isBadRequest() }
        }
        .andDo {
            print()
        }
        .andExpect {
            jsonPath("$.code") { value("C001") }
            jsonPath("$.message") { value("Invalid input value") }
            jsonPath("$.errors[0].reason") { value("require title") }
            jsonPath("$.errors") { haveSize<Any>(1) }

        }
        .andReturn()
    }


}