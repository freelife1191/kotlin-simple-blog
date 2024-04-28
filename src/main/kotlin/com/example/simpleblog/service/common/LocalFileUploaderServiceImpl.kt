package com.example.simpleblog.service.common

import com.example.simpleblog.config.aop.CustomAopObject
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

/**
 * Created by mskwon on 4/28/24.
 */
@Service
class LocalFileUploaderServiceImpl(

): FileUploaderService {
    private val log = mu.KotlinLogging.logger {}

    val localImgFolderPath = "src/main/resources/static/postImg"
    @PostConstruct
    fun init() = CustomAopObject.wrapTryCatchWithVoidFunc {

        val directory = File(localImgFolderPath)

        if (!directory.exists()) {
            log.info { "create $localImgFolderPath directory " }
            Files.createDirectories(Paths.get(localImgFolderPath))
        }
    }

    override fun upload(file: MultipartFile) {
        Files.write(Path(localImgFolderPath + "/" + file.originalFilename), file.bytes)
    }
}