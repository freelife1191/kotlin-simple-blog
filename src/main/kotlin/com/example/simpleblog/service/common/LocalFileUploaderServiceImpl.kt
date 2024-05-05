package com.example.simpleblog.service.common

import com.example.simpleblog.config.aop.CustomAopObject
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

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

    override fun upload(file: MultipartFile): String {

        val uuid = UUID.randomUUID().toString()
        val fileName = uuid + "_" + file.originalFilename
        val imgFilePath = Paths.get("$localImgFolderPath/$fileName")

        Files.write(imgFilePath, file.bytes)

        val url = ResourceUtils.getURL(imgFilePath.toString())

        log.info { "url==>$url" }

        return url.toString()
    }
}