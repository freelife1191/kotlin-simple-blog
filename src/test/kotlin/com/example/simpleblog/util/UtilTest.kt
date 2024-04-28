package com.example.simpleblog.util

import com.example.simpleblog.service.common.FileUploaderService
import com.example.simpleblog.service.common.LocalFileUploaderServiceImpl
import faker.com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by mskwon on 4/28/24.
 */
class UtilTest {

    private val log = KotlinLogging.logger {}

    val mapper = ObjectMapper()

    @Test
    fun localFileUploaderTest() {
        val fileUploader: FileUploaderService = LocalFileUploaderServiceImpl()
        val path: Path = Paths.get("src/test/resources/img/favicon.png")

        val name = "favicon.png"
        val originalFileName = "favicon.png"
        val contentType = MediaType.IMAGE_PNG_VALUE
        var content = Files.readAllBytes(path)
        val mockFile: MultipartFile = MockMultipartFile(
            name, originalFileName, contentType, content
        )
        fileUploader.upload(mockFile)
    }
}