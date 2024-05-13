package kr.argonaut.argonaut.infra.plugin

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.domain.plugin.PluginManifestLoader
import kr.argonaut.argonaut.domain.plugin.model.PluginManifests
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Component

@Component
class PluginManifestLoaderImpl(
    private val serverManifestLoader: ServerManifestLoader,
): PluginManifestLoader {
    private val logger = getLogger()

    override suspend fun loadAll(env: Environment): PluginManifests {
        logger.info(">load plugin manifest")

        val serverManifest = serverManifestLoader.load(env)

        return PluginManifests(
            jarManifests = serverManifest.pluginJars,
            configManifests = serverManifest.pluginConfigs,
        ).also { manifests ->
            logger.info("=jarManifests: ${manifests.jarManifests.joinToString(",")}")
            logger.info("=configManifests: ${manifests.configManifests.joinToString(",")}")
        }
    }
}