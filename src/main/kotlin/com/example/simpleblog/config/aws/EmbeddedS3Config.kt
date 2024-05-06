package com.example.simpleblog.config.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.findify.s3mock.S3Mock
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by mskwon on 5/6/24.
 */
@Configuration
class EmbeddedS3Config {

    private val log = KotlinLogging.logger { }
    final val port = 7777
    private var api = S3Mock.Builder().withPort(port).withInMemoryBackend().build()
    private val serverEndpoint = "http://localhost:$port"
    private val region = "ap-northeast-2"

    @PostConstruct
    fun init() {
        log.info("mocking S3 API Start!!")
        this.api.start()
    }

    @Bean
    fun amazonS3(): AmazonS3 {
        val endpoint = EndpointConfiguration(serverEndpoint, region)
        val client: AmazonS3 = AmazonS3ClientBuilder
            .standard()
            .withPathStyleAccessEnabled(true)
            .withEndpointConfiguration(endpoint)
            .withCredentials(AWSStaticCredentialsProvider (AnonymousAWSCredentials()))
            .build()
        log.info { "embeddedAmazonS3===> $client" }
        return client
    }

    @PreDestroy
    fun destroy() {
        log.info { "s3 Mock API ShutDown" }
        this.api.shutdown()
    }
}