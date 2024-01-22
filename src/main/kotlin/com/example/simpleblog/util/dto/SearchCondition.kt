package com.example.simpleblog.util.dto

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Created by mskwon on 2024/01/21.
 */
class SearchCondition (
    val searchType: SearchType?,
    val keyword: String?
) {
}

enum class SearchType {
    EMAIL, TITLE, CONTENT;

    companion object {
        @JsonCreator
        fun from(searchType: String?): SearchType {
            return SearchType.valueOf(searchType?.uppercase().toString())
        }
    }
}
