package com.example.simpleblog.api

import com.example.simpleblog.core.base.BaseRestControllerTest
import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.domain.member.MemberSaveReq
import com.example.simpleblog.domain.member.Role
import com.example.simpleblog.util.JsonUtils
import com.example.simpleblog.util.value.CommonResDto
import com.fasterxml.jackson.core.type.TypeReference
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

/**
 * Created by mskwon on 2023/07/23.
 */
class MemberControllerTest : BaseRestControllerTest() {

    @Test
    fun findAll() {
        val result = mockMvc.get("/members") {
            param("page","0")
            param("size", "10")
        }
            .andExpect {
                status { isOk() }
            }
            /*.andDo {
                print()
            }*/
            .andReturn()
        val content = result.response.contentAsString
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<Page<MemberRes>>>() {})
        response.data.content.forEach { println(JsonUtils.toMapperPrettyJson(it)) }

        // members.forEach { println(it) }
    }

    @Test
    fun findById() {
        val result = mockMvc.get("/member/{id}",1) {}
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
            .andReturn()
        val content = result.response.contentAsString
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<MemberRes>>() {})
        println(JsonUtils.toMapperPrettyJson(response))
    }

    @Test
    fun findByIdError() {
        mockMvc.get("/member/{id}",1000) {}
            .andExpect {
                status { is5xxServerError() }
            }
            .andDo {
                print()
            }
            .andReturn()
    }

    @Test
    @Transactional
    fun deleteById() {
        val result = mockMvc.delete("/member/{id}",1) {}
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
        val result = mockMvc.post("/member") {
            jsonContent(
                MemberSaveReq(
                    email = "aaa@aaa.com",
                    password = "1234",
                    role = Role.USER)
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
        val response = JsonUtils.getObjectMapper().readValue(content, object : TypeReference<CommonResDto<MemberRes>>() {})
        println(JsonUtils.toMapperPrettyJson(response))
    }

    @Test
    fun saveError() {
        mockMvc.post("/member") {
            jsonContent(
                MemberSaveReq(
                    email = null,
                    password = "1234",
                    role = Role.USER)
            )
        }
        .andExpect {
            status { isBadRequest() }
        }
        .andDo {
            print()
        }
        .andReturn()
    }
}