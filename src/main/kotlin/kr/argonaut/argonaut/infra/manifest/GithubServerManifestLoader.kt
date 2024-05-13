package kr.argonaut.argonaut.infra.manifest

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifest
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.infra.github.GithubService
import kr.argonaut.argonaut.infra.manifest.data.GithubServerManifest
import kr.argonaut.argonaut.lib.getLogger

class GithubServerManifestLoader(
    private val githubService: GithubService,
): ServerManifestLoader {
    private val logger = getLogger()

    companion object {
        private const val ORGANIZATION_ID = "ArgonautMC"
        private const val REPOSITORY_ID = "medeia"
    }

    override suspend fun load(env: Environment): ServerManifest {
        logger.info(">load server manifest")

        val path = "/server/$env.profile.json"

        val data = githubService.readFile(
            ORGANIZATION_ID,
            REPOSITORY_ID,
            "main",
            path,
            GithubServerManifest::class.java
        ).toManifest()

        return data.also { manifests ->
            logger.info("=spigotVersion: ${manifests.spigotVersion}")
            logger.info("=jarManifests: ${manifests.pluginJars.joinToString(",")}")
            logger.info("=configManifests: ${manifests.pluginConfigs.joinToString(",")}")
        }
    }
}