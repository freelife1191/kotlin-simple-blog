package com.example.simpleblog.util.value

import org.springframework.http.HttpStatus

/**
 * Created by mskwon on 2023/07/16.
 */
data class CommonResDto<T>(
    val resultCode: HttpStatus,
    var resultMsg: String,
    val data: T
) {
    fun reflectVersion(apiVersion: String) {
        this.resultMsg = "version: $apiVersion / ${this.resultMsg}"
    }
}