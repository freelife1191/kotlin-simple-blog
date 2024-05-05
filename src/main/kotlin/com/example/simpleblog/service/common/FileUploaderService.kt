package com.example.simpleblog.service.common

import org.springframework.web.multipart.MultipartFile

/**
 * Created by mskwon on 4/28/24.
 */
interface FileUploaderService {
    fun upload(file: MultipartFile): String
}