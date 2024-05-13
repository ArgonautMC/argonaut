package kr.argonaut.argonaut.infra.plugin

import kr.argonaut.argonaut.lib.mapAsync
import kotlinx.coroutines.awaitAll
import kr.argonaut.argonaut.domain.plugin.RemotePluginLoader
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.plugin.model.PluginJar
import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest
import kr.argonaut.argonaut.infra.s3.S3Service
import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Component
import kotlin.io.path.Path

private const val PLUGIN_BUCKET_ID = "argonaut--plugins"

@Component
class S3RemotePluginLoader(
    private val s3Service: S3Service,
    env: Environment,
): RemotePluginLoader {
    private val logger = getLogger()
    private val pluginConfigBucketId = "argonaut-$env--plugin-configs"

    override suspend fun load(manifest: PluginJarManifest): PluginJar {
        val s3PluginFolder = getPluginFolderS3Key(manifest)

        logger.info("pluginJarKey = ${s3PluginFolder.pluginJarKey}")

        val bytes = s3Service.getObject(PLUGIN_BUCKET_ID, s3PluginFolder.pluginJarKey).toTypedArray()
        return PluginJar(bytes, manifest)
    }

    override suspend fun load(manifest: PluginConfigManifest): PluginConfig {
        val s3PluginConfigFolder = "${manifest.s3FolderName}/"

        val keys = s3Service.listObjects(pluginConfigBucketId, s3PluginConfigFolder).mapNotNull { it.key }

        val bytesByPath = keys.mapAsync { key ->
            Path(key.removePrefix(s3PluginConfigFolder)) to s3Service.getObject(pluginConfigBucketId, key).toTypedArray()
        }.awaitAll().toMap()

        return PluginConfig(bytesByPath, manifest)
    }

    private fun getPluginFolderS3Key(manifest: PluginJarManifest): S3PluginFolder {
        val pluginFolderKey = listOf(
            manifest.groupId.split("."),
            manifest.artifactId.split("."),
        ).flatten().joinToString("/")
        return S3PluginFolder(pluginFolderKey, manifest.version)
    }
}