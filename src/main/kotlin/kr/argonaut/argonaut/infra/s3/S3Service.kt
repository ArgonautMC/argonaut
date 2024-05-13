package kr.argonaut.argonaut.infra.s3

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.model.ListObjectsV2Request
import aws.sdk.kotlin.services.s3.model.Object
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.content.asByteStream
import aws.smithy.kotlin.runtime.content.toByteArray
import kotlinx.coroutines.runBlocking
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Service
import java.io.File

@Service
class S3Service(
    private val s3Client: S3Client,
) {
    private val logger = getLogger()

    suspend fun listObjects(bucket: String, prefix: String): List<Object> {
        logger.trace("s3/list-objects-v2 --bucket $bucket --prefix $prefix")
        val request = ListObjectsV2Request {
            this.prefix = prefix
            this.bucket = bucket
        }
        return s3Client.listObjectsV2(request).contents
            ?.filterNot { it.key?.endsWith("/") ?: true }
            ?: emptyList()
    }

    suspend fun getObject(bucket: String, key: String): ByteArray {
        val request = GetObjectRequest {
            this.key = key
            this.bucket = bucket
        }

        logger.trace("s3/get --bucket $bucket --key $key")
        return runBlocking {
            s3Client.getObject(request) { response ->
                requireNotNull(response.body).toByteArray()
            }
        }
    }

    suspend fun putObject(bucket: String, key: String, file: File) {
        val request = PutObjectRequest {
            this.bucket = bucket
            this.key = key
            this.metadata = emptyMap()
            this.body = file.asByteStream()
        }

        s3Client.putObject(request)
    }

    suspend fun putObject(bucket: String, key: String, array: ByteArray) {
        val request = PutObjectRequest {
            this.bucket = bucket
            this.key = key
            this.metadata = emptyMap()
            this.body = ByteStream.fromBytes(array)
        }

        s3Client.putObject(request)
    }
}