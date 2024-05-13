package kr.argonaut.argonaut.infra.github

import org.kohsuke.github.GitHubBuilder

class GithubCredentialsProviderContainer {
    var credentialsProvider: BasicGithubCredentialsProvider? = null
}

class BasicGithubCredentialsProvider(
    private val id: String,
    private val token: String,
) {
    fun buildWithCredentials(builder: GitHubBuilder): GitHubBuilder =
        builder.withOAuthToken(token, id)
}
