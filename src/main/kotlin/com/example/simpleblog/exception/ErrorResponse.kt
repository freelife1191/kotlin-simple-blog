package com.example.simpleblog.exception

import org.springframework.validation.AbstractBindingResult
import org.springframework.validation.BindingResult

/**
 * Created by mskwon on 2023/08/19.
 */
class ErrorResponse (
    errorCode: ErrorCode,
    var errors: List<FieldError> = ArrayList(),
    var code: String = errorCode.code,
    var message: String = errorCode.message
) {



    class FieldError private constructor(
        val field: String,
        val value: String,
        val reason: String?
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                val fieldError = bindingResult.fieldErrors
                return fieldError.map {error ->
                    FieldError(
                        field = error.field,
                        value = if (error.rejectedValue == null) "" else error.rejectedValue.toString(),
                        reason = error.defaultMessage
                    )

                }
            }
        }
    }

    companion object {
        fun of(code: ErrorCode, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(
                errorCode = code,
                errors = FieldError.of(bindingResult)
            )
        }

        fun of(code: ErrorCode): ErrorResponse {
            return ErrorResponse(
                errorCode = code
            )
        }
    }
}