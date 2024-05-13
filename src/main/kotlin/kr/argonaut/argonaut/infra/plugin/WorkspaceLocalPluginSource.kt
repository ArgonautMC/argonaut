package kr.argonaut.argonaut.infra.plugin

import kr.argonaut.argonaut.domain.workspace.WorkspaceConfiguration
import kr.argonaut.argonaut.domain.workspace.WorkspacePaths
import kr.argonaut.argonaut.domain.plugin.LocalPluginSource
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.domain.plugin.model.PluginJar
import kr.argonaut.argonaut.lib.getLogger
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component
import java.io.File
import kotlin.io.path.Path

@Component
class WorkspaceLocalPluginSource(
    workspaceConfiguration: WorkspaceConfiguration,
): LocalPluginSource {
    private val logger = getLogger()
    private val workspacePluginFolder = workspaceConfiguration.getWorkspace()
        .resolve(WorkspacePaths.PLUGIN_FOLDER)

    override suspend fun removeAll(
        onlyJar: Boolean,
        vararg excludes: String,
    ): Int {
        val legacies = workspacePluginFolder.listFiles()
            ?.filterNot { excludes.any { exclude -> Regex(exclude).matches(it.name) } }
            ?.filterNot { onlyJar && !it.name.endsWith(".jar") }

        legacies?.forEach {
            if(it.isDirectory) FileUtils.deleteDirectory(it)
            else it.delete()
        }

        return legacies?.size ?: 0
    }

    override suspend fun add(plugin: PluginJar) {
        val m = plugin.jarManifest
        val localPluginName = "${m.groupId}.${m.artifactId}.${m.version}.jar"
        val path = "$workspacePluginFolder/$localPluginName"

        logger.info("Add plugin jar to file source '$path'")
        FileUtils.writeByteArrayToFile(File(path), plugin.bytes.toByteArray())
        logger.info("Add plugin jar to file source '$path' complete!")
    }

    override suspend fun add(config: PluginConfig) {
        val rootPath = Path("$workspacePluginFolder/${config.configManifest.localFolderName}")

        logger.info("Add plugin config '${config.configManifest.name}'")
        config.bytesByPath.map { (path, bytes) ->
            val file = rootPath.resolve(path).toFile()
            logger.info("Add plugin config to file source '${file.path}'")
            FileUtils.writeByteArrayToFile(file, bytes.toByteArray())
            logger.info("Add plugin config to file source '${file.path}' complete!")
        }
    }
}