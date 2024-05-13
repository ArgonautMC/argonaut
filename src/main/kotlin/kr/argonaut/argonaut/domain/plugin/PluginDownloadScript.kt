package kr.argonaut.argonaut.domain.plugin

import kr.argonaut.argonaut.lib.mapAsync
import kotlinx.coroutines.Deferred
import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.plugin.model.PluginJar
import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Service

@Service
class PluginDownloadScript(
    private val remotePluginLoader: RemotePluginLoader,
    private val pluginManifestLoader: PluginManifestLoader,
    private val localPluginSource: LocalPluginSource,
    private val environment: Environment,
) {
    private val logger = getLogger()

    suspend fun download() {
        logger.info("Load plugins...")
        val manifests = pluginManifestLoader.loadAll(environment)

        val pluginJars = loadRemotePluginJars(manifests.jarManifests)
        val pluginConfigs = loadRemotePluginConfigs(manifests.configManifests)

        removeLocalPlugins()

        downloadPluginJars(pluginJars)
        downloadPluginConfigs(pluginConfigs)
        logger.info("Plugins are loaded!")
    }

    private suspend fun loadRemotePluginConfigs(
        configManifests: List<PluginConfigManifest>
    ): List<Deferred<PluginConfig>>  {
        logger.info("> Load plugin configs")

        return configManifests.mapAsync { manifest ->
            remotePluginLoader.load(manifest)
        }.also { pluginConfigs ->
            logger.info("= ${pluginConfigs.size} remote configs are loaded!")
        }
    }

    private suspend fun loadRemotePluginJars(
        jarManifests: List<PluginJarManifest>
    ): List<Deferred<PluginJar>> {
        logger.info("> Load plugin jars")

        return jarManifests.mapAsync { manifest ->
            remotePluginLoader.load(manifest)
        }.also { plugins ->
            logger.info("= ${plugins.size} remote plugins are loaded!")
        }
    }

    private suspend fun removeLocalPlugins() {
        logger.info("Delete local plugin jars and configs...")
        localPluginSource.removeAll(onlyJar = false)
    }

    private suspend fun downloadPluginJars(pluginJars: List<Deferred<PluginJar>>) {
        pluginJars.mapAsync { plugin ->
            localPluginSource.add(plugin.await())
        }
        logger.info("Remote plugin jars are downloaded!")
    }

    private suspend fun downloadPluginConfigs(pluginConfigs: List<Deferred<PluginConfig>>) {
        pluginConfigs.mapAsync { config ->
            localPluginSource.add(config.await())
        }
        logger.info("Remote plugin configs are downloaded!")
    }
}