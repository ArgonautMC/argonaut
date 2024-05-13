package kr.argonaut.argonaut.infra.github

import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder

class GithubClient(
    val github: GitHub
) {
    companion object {
        fun fromEnvironment(
            builder: GithubCredentialsProviderContainer.() -> Unit
        ): GithubClient {
            val container = GithubCredentialsProviderContainer()
            container.run(builder)
            val provider = requireNotNull(container.credentialsProvider)
            val github = GitHubBuilder()
                .let(provider::buildWithCredentials)
                .build()
            return GithubClient(github)
        }
    }
}