package kr.argonaut.argonaut.infra.github

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GithubConfiguration(
    @Value("\${github.id}")
    private val id: String,
    @Value("\${github.token}")
    private val token: String,
) {
    @Bean
    fun githubClient() =
        GithubClient.fromEnvironment {
            credentialsProvider = BasicGithubCredentialsProvider(id, token)
        }
}
