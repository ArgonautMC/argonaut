package kr.argonaut.argonaut.infra.plugin

import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.plugin.LocalPluginLoader
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.domain.workspace.WorkspaceConfiguration
import kr.argonaut.argonaut.domain.workspace.WorkspacePaths
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Path

@Service
class WorkspaceLocalPluginLoader(
    workspaceConfiguration: WorkspaceConfiguration,
): LocalPluginLoader {
    private val logger = getLogger()

    private val pluginFolder = workspaceConfiguration.getWorkspace()
        .resolve(WorkspacePaths.PLUGIN_FOLDER)

    override suspend fun load(manifest: PluginConfigManifest): PluginConfig {
        val configFolder = pluginFolder.resolve(manifest.localFolderName)

        if (!configFolder.exists() || !configFolder.isDirectory) {
            logger.warn("config folder '${configFolder.absolutePath}' does not exists. (or is not directory)")
            return PluginConfig(
                bytesByPath = emptyMap(),
                configManifest = manifest,
            )
        }

        val bytesByPath = collectFiles(configFolder)

        return PluginConfig(
            bytesByPath = bytesByPath,
            configManifest = manifest,
        )
    }
}

private fun collectFiles(root: File): Map<Path, Array<Byte>> {
    val filesMap = mutableMapOf<Path, Array<Byte>>()
    collectFilesRecursive(root, root, filesMap)
    return filesMap
}

private fun collectFilesRecursive(root: File, currentDir: File, filesMap: MutableMap<Path, Array<Byte>>) {
    val files = currentDir.listFiles() ?: return
    for (file in files) {
        if (file.isDirectory) {
            collectFilesRecursive(root, file, filesMap)
        } else {
            val relativeContentPath = root.toPath().relativize(file.toPath()).toString()
            val relativePath = Path.of("${root.name}/$relativeContentPath" )
            filesMap[relativePath] = file.readBytes().toTypedArray()
        }
    }
}
