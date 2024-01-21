package com.example.simpleblog.config

import com.example.simpleblog.util.dto.SearchType
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Created by mskwon on 2024/01/21.
 */
class WebMvcConfig (

): WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToEnumConverter())
    }
}

class StringToEnumConverter: Converter<String, SearchType> {
    override fun convert(source: String): SearchType? {
        println("source==>$source")
        return SearchType.valueOf(source.uppercase())
    }

}
