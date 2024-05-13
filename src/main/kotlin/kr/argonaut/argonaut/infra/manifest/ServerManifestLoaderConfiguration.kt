package kr.argonaut.argonaut.infra.manifest

import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.infra.github.GithubService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServerManifestLoaderConfiguration {
    @Bean
    fun serverManifestLoader(githubService: GithubService): ServerManifestLoader =
        ApplicationCachedServerManifestLoader(
            GithubServerManifestLoader(githubService)
        )
}