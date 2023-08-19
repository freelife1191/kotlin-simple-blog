package com.example.simpleblog.exception

/**
 * Created by mskwon on 2023/08/19.
 */
sealed class EntityNotFoundException(message: String?): BusinessException(message, ErrorCode.ENTITY_NOT_FOUND)

class MemberNotFoundException(id: Long): EntityNotFoundException("$id not found")