package kr.argonaut.argonaut.domain.plugin

import kotlinx.coroutines.Deferred
import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.lib.mapAsync
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Component

@Component
class PluginUploadScript(
    private val localPluginLoader: LocalPluginLoader,
    private val pluginManifestLoader: PluginManifestLoader,
    private val remotePluginSource: RemotePluginSource,
    private val environment: Environment,
) {
    private val logger = getLogger()

    suspend fun upload() {
        logger.info("Save plugins...")
        val manifests = pluginManifestLoader.loadAll(environment)
        val pluginConfigs = loadLocalPluginConfigs(manifests.configManifests)

        uploadPluginConfigs(pluginConfigs)
        logger.info("Plugins are saved!")
    }

    private suspend fun loadLocalPluginConfigs(
        configManifests: List<PluginConfigManifest>
    ): List<Deferred<PluginConfig>> {
        logger.info("> Load local plugin configs")

        return configManifests.mapAsync { manifest ->
            localPluginLoader.load(manifest)
        }.also { pluginConfigs ->
            logger.info("= ${pluginConfigs.size} local configs are loaded!")
        }
    }

    private suspend fun uploadPluginConfigs(pluginConfigs: List<Deferred<PluginConfig>>) {
        pluginConfigs.mapAsync { config ->
            remotePluginSource.add(config.await())
        }
        logger.info("Local plugin configs are uploaded!")
    }
}