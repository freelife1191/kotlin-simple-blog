package com.example.simpleblog.api

import com.example.simpleblog.core.base.BaseRestControllerTest
import com.example.simpleblog.domain.member.Member
import com.example.simpleblog.domain.member.MemberRes
import com.example.simpleblog.util.JsonUtils
import com.example.simpleblog.util.value.CommonResDto
import com.fasterxml.jackson.core.type.TypeReference
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.test.web.servlet.get

/**
 * Created by mskwon on 2023/07/23.
 */
class MemberControllerTest : BaseRestControllerTest() {

    @Test
    fun members() {
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
}