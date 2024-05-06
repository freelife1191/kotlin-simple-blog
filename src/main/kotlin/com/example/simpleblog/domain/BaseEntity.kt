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
    override var id: Long = 0,
    @Column(nullable = false, updatable = false)
    @CreatedDate var createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate var updatedAt: LocalDateTime = LocalDateTime.now()
): BaseEntityId(id) {
    protected fun setBaseDtoProperty(dto: BaseDto) {
        dto.id = this.id
        dto.createdAt = this.createdAt
        dto.updatedAt = this.updatedAt
    }
}

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntityId (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long
)