package com.example.simpleblog.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * Created by mskwon on 2023/07/02.
 */
@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntity (
    @CreatedDate var createdAt: LocalDateTime? = null,
    @LastModifiedDate var updatedAt: LocalDateTime? = null
): BaseEntityId()

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntityId (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
)