package kr.argonaut.argonaut.infra.manifest

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifest
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.infra.manifest.data.GithubServerManifest
import java.net.URL

class EnvironmentServerManifestLoader(
    private val objectMapper: ObjectMapper,
): ServerManifestLoader {
    override suspend fun load(env: Environment): ServerManifest {
        require(env != Environment.PRODUCTION) {
            "Production server can't use EnvironmentServerManifestLoader."
        }

        return withContext(Dispatchers.IO) {
            val url = getManifestUrlByEnvironment()
            val content = url.readText(charset = Charsets.UTF_8)

            return@withContext toManifest(content)
        }
    }

    private fun getManifestUrlByEnvironment(): URL =
        System.getenv("SERVER_MANIFEST_URL").let(::URL)

    private fun toManifest(response: String): ServerManifest =
        objectMapper.readValue(
            response,
            GithubServerManifest::class.java,
        ).toManifest()
}
