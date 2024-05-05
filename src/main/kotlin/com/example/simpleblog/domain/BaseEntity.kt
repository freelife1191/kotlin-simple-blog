package com.example.simpleblog.domain

import com.fasterxml.jackson.annotation.JsonFormat
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
    @Column(nullable = false, updatable = false)
    @CreatedDate var createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate var updatedAt: LocalDateTime = LocalDateTime.now()
): BaseEntityId()

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntityId (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
)