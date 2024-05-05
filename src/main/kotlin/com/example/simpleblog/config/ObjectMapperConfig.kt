package com.example.simpleblog.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.addSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by mskwon on 5/5/24.
 */
@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(LocalDateTime::class, CustomLocalDateTimeSerializer())
        mapper.registerModule(javaTimeModule)
        // mapper.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
        mapper.registerModule(
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
        return mapper
    }

    /**
     * DTO 응답 LocalDateTime Format 설정
     * @property dateTimeFormat String
     * @property formatter (DateTimeFormatter..DateTimeFormatter?)
     */
    class CustomLocalDateTimeSerializer: JsonSerializer<LocalDateTime>() {
        private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
        private val formatter = DateTimeFormatter.ofPattern(dateTimeFormat, Locale.KOREA)
        override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
            gen.writeString(formatter.format(value))
        }
    }

}