package kr.argonaut.argonaut.infra.s3

import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.http.engine.crt.CrtHttpEngine
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Configuration(
    @Value("\${aws.s3.accessKey}")
    private val accessKeyId: String,
    @Value("\${aws.s3.secretKey}")
    private val secretAccessKey: String,
) {
    @Bean
    fun amazonS3Client(): S3Client {
        return runBlocking {
            S3Client.fromEnvironment {
                httpClient = CrtHttpEngine()
                region = "ap-northeast-2"
                credentialsProvider = BasicAwsCredentialsProvider(
                    accessKeyId,
                    secretAccessKey,
                )
            }
        }
    }
}