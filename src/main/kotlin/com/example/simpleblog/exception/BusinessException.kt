package com.example.simpleblog.exception

/**
 * Created by mskwon on 2023/08/19.
 */
sealed class BusinessException: RuntimeException {

    private var errorCode: ErrorCode
        get() {
            return this.errorCode
        }

    constructor(errorCode: ErrorCode): super(errorCode.message) {
        this.errorCode = errorCode
    }

    constructor(message: String?, errorCode: ErrorCode) : super(message) {
        this.errorCode = errorCode
    }
}