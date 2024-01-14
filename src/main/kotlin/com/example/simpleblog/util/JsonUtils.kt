package com.example.simpleblog.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.cloud.openfeign.support.PageJacksonModule
import org.springframework.cloud.openfeign.support.SortJacksonModule
import java.io.InputStream
import kotlin.reflect.KClass

/**
 * The type Json utils.
 *
 * Created by KMS on 2021/05/20.
 */
class JsonUtils {

    private var mapper: ObjectMapper = jacksonObjectMapper()

    companion object {

        fun getObjectMapper() = getInstance().mapper
            .registerModules(
                PageJacksonModule(),
                SortJacksonModule(),
                JavaTimeModule(),
                KotlinModule.Builder()
                    .disable(KotlinFeature.StrictNullChecks)
                    .disable(KotlinFeature.NullIsSameAsDefault)
                    .disable(KotlinFeature.NullToEmptyMap)
                    .disable(KotlinFeature.NullToEmptyCollection)
                    .disable(KotlinFeature.SingletonSupport)
                    .build()
            )
            .registerKotlinModule()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        // .enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
        // .enable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
        // .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)

        /**
         * Gets instance.
         *
         * @return the instance
         */
        private fun getInstance() = JsonUtils()

        /**
         * To ObjectMapper pretty json string
         * @param object
         * @return
         */
        @Throws(JsonProcessingException::class)
        fun toMapperJson(any: Any): String = getObjectMapper().writeValueAsString(any)

        /**
         * To ObjectMapper pretty json string
         * @param any
         * @return
         */
        @Throws(JsonProcessingException::class)
        fun toMapperPrettyJson(any: Any?): String {
            return try {
                getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(any)
            } catch (e: Exception) {
                println(e.message)
                ""
            }
        }

        /**
         * To ObjectMapper Mapping json string
         * @param obj JSON String
         * @param clz 변환할 오브젝트 클래스
         * @param <T>
         * @return
        </T> */
        @Throws(JsonProcessingException::class)
        fun <T : Any> toMapperObject(obj: String, clz: KClass<T>): T = getObjectMapper().readValue(obj, clz.java)
        /**
         * To ObjectMapper Mapping json string
         * @param inputStream InputStream
         * @param clz KClass<T>
         * @return T
         * @throws JsonProcessingException
         */
        @Throws(JsonProcessingException::class)
        fun <T : Any> toMapperObject(inputStream: InputStream?, clz: KClass<T>): T = getObjectMapper().readValue(inputStream, clz.java)
        /**
         * To ObjectMapper Mapping json string
         * @param obj JSON String
         * @param type 변환할 오브젝트 클래스
         * @param <T>
         * List 와 같은 형태는 TypeReference 형으로 처리
         * new TypeReference<List></List><Object>() {}
         * @return
        </Object></T> */
        @Throws(JsonProcessingException::class)
        fun <T> toMapperObject(obj: String, type: TypeReference<T>): T = getObjectMapper().readValue(obj, type)
    }
}