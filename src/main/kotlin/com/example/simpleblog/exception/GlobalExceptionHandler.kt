package com.example.simpleblog.exception

import jakarta.persistence.NoResultException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Created by mskwon on 2023/08/20.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    val log = KotlinLogging.logger {  }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.error { "handleMethodArgumentNotValidException $e" }
        val of = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.bindingResult)
        return ResponseEntity(of, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MemberNotFoundException::class)
    fun handleMemberNotFoundException(e: MemberNotFoundException): ResponseEntity<ErrorResponse> {
        log.error { "handleMemberNotFoundException $e" }
        val of = ErrorResponse.of(ErrorCode.MEMBER_NOT_FOUND)
        return ResponseEntity(of, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        log.error { "handleEntityNotFoundException $e" }
        val of = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND)
        return ResponseEntity(of, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NoResultException::class)
    fun handleNoResultException(e: NoResultException): ResponseEntity<ErrorResponse> {
        log.error { "handleMethodArgumentNotValidException $e" }
        val of = ErrorResponse.of(ErrorCode.NO_RESULT)
        return ResponseEntity(of, HttpStatus.NO_CONTENT)
    }
}