package com.example.simpleblog.service

import com.example.simpleblog.domain.post.*
import com.example.simpleblog.service.common.FileUploaderService
import com.example.simpleblog.service.common.LocalFileUploaderServiceImpl
import com.example.simpleblog.util.dto.SearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
 * Created by mskwon on 2023/07/03.
 */
@Service
class PostService(
    private val postRepository: PostRepository,
    private val localS3FileUploaderServiceImpl: FileUploaderService
) {
    // @PreAuthorize("hasRole('ADMIN')")
    // @PostAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // @Secured(*["ROLE_ADMIN", "ROLE_USER"])
    @Transactional(readOnly = true)
    fun findPosts(pageable: Pageable, searchCondition: SearchCondition): Page<PostRes> =
        postRepository.findPosts(pageable, searchCondition).map { it.toDto() }

    @Transactional
    fun savePost(dto: PostSaveReq): PostRes {
        return postRepository.save(dto.toEntity()).toDto()
    }

    @Transactional
    fun deletePost(id: Long) {
        return postRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findPostById(id: Long): PostRes {
        return postRepository.findById(id).orElseThrow().toDto();
    }

    fun savePostImg(image: MultipartFile): String {
        return localS3FileUploaderServiceImpl.upload(image)
    }
}