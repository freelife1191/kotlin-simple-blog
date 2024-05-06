package com.example.simpleblog.domain.comment

import com.example.simpleblog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lombok.ToString

/**
 * Created by mskwon on 5/6/24.
 */
@Entity
@ToString
@Table(name = "comment_closure")
class CommentClosure(
    override var id: Long,
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_ancestor", nullable = true)
    var idAncestor: Comment?,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_descendant", nullable = true)
    var idDescendant: Comment,
    @Column(name = "depth")
    var depth: Int,
): BaseEntity(id)