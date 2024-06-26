package com.example.simpleblog.util

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.io.PrintWriter

/**
 * Created by mskwon on 2024/01/14.
 */
object Script {

    fun responseData(resp: HttpServletResponse?, jsonData: String?) {
        val out: PrintWriter
        println("응답 데이터: $jsonData")
        resp?.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        try {
            out = resp?.writer!!
            out.println(jsonData)
            out.flush() // 버퍼 비우기
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}