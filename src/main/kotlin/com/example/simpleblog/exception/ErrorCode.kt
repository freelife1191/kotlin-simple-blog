package com.example.simpleblog.exception

import com.fasterxml.jackson.annotation.JsonFormat

/**
 * Created by mskwon on 2023/08/19.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode (
    val code: String,
    val message: String
) {
    INVALID_INPUT_VALUE("C001", "Invalid input value"),
    ENTITY_NOT_FOUND("C002", "Entity not found")
}