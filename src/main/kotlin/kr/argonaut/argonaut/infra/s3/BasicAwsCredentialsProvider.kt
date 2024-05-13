package kr.argonaut.argonaut.infra.s3

import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import aws.smithy.kotlin.runtime.collections.Attributes
import aws.smithy.kotlin.runtime.collections.mutableAttributes
import aws.smithy.kotlin.runtime.collections.setIfValueNotNull
import aws.smithy.kotlin.runtime.identity.IdentityAttributes

private const val PROVIDER_NAME = "Basic"

class BasicAwsCredentialsProvider(
    private val accessKeyId: String,
    private val secretAccessKey: String,
): CredentialsProvider {
    override suspend fun resolve(attributes: Attributes): Credentials {
        val attr = mutableAttributes().apply {
            setIfValueNotNull(IdentityAttributes.ProviderName, PROVIDER_NAME)
        }
        return Credentials(
            accessKeyId,
            secretAccessKey,
            null,
            null,
            attributes = attr
        )
    }
}
