package com.example.simpleblog.domain

import java.time.LocalDateTime

/**
 * Created by mskwon on 5/5/24.
 */
abstract class BaseDto(
    var id: Long = 0,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
}