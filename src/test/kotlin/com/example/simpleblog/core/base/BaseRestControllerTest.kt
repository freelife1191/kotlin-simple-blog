package com.example.simpleblog.core.base

import com.example.simpleblog.core.annotation.IntegrationTest
import com.example.simpleblog.util.JsonUtils
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.BeforeEach
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.KClass


/**
 * Created by mskwon on 2023/07/23.
 */
// @AutoConfigureRestDocs
// @ExtendWith(RestDocumentationExtension::class)
@IntegrationTest
class BaseRestControllerTest {
    lateinit var mapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(
        webApplicationContext: WebApplicationContext,
        // restDocumentationContextProvider: RestDocumentationContextProvider,
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            /*
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(
                    restDocumentationContextProvider
                ).operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            */
            .build()
    }

    fun MockHttpServletRequestDsl.jsonContent(value: Any) {
        content = mapper.writeValueAsString(value)
        contentType = MediaType.APPLICATION_JSON
    }

    /**
     * 파일 업로드 데이터 생성
     * @param originalFileName 원본파일명
     * @param reqFileName 요청파일명(API에서 받는 이름)
     * @return
     */
    fun getMultipartFile(originalFileName: String?, reqFileName: String?): MockMultipartFile {
        return getMultipartFile(null, originalFileName, reqFileName)
    }

    /**
     * 파일 업로드 데이터 생성
     * @param originalFileName 원본파일명
     * @param reqFileName 요청파일명(API에서 받는 이름)
     * @return
     */
    fun getMultipartFile(
        fileMiddlePath: String?,
        originalFileName: String?,
        reqFileName: String?,
    ): MockMultipartFile {
        var filePath = Paths.get("src", "test", "resources", "file")
        if (StringUtils.isNotEmpty(fileMiddlePath)) filePath = Path.of(filePath.toString(), fileMiddlePath)
        filePath = Paths.get(filePath.toString(), originalFileName)
        // File file = new File(String.valueOf(filePath));

        //        String contentType = "";
        //        String contentType = "image/png";
        //        String contentType = "application/zip";
        //            File file = new File(String.valueOf(path));
        return MockMultipartFile(reqFileName!!, originalFileName, null, Files.readAllBytes(filePath))
    }

    fun readFile(path: Path?, filename: String?): File {
        if (path == null) {
            throw RuntimeException("is Required path!!")
        }
        if (StringUtils.isEmpty(filename)) {
            throw RuntimeException("is Required name!!")
        }
        return File(Paths.get("src", "test", "resources", path.toString(), filename).toString())
    }

    fun getTestJsonData(name: String): String? {
        if (StringUtils.isEmpty(name)) {
            throw RuntimeException("is Required name!!")
        }
        val result: String
        val jsonData = readFile(Paths.get("json"), "$name.json")
        result = try {
            val jsonMap: Map<String?, Any?>? =
                JsonUtils.getObjectMapper().readValue(jsonData, object : TypeReference<Map<String?, Any?>?>() {})
            JsonUtils.getObjectMapper().writeValueAsString(jsonMap)
        } catch (e: IOException) {
            throw RuntimeException(e.message)
        }
        return result
    }
}