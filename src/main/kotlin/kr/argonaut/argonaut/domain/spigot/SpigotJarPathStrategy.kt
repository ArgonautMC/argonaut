package kr.argonaut.argonaut.domain.spigot

import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion
import kr.argonaut.argonaut.domain.workspace.WorkspaceConfiguration
import kr.argonaut.argonaut.domain.workspace.WorkspacePaths
import java.io.File

object SpigotJarPathStrategy {
    fun getJarFile(
        workspaceConfiguration: WorkspaceConfiguration,
        version: SpigotVersion
    ): File {
        val serverFolder = workspaceConfiguration.getWorkspace()
            .resolve(WorkspacePaths.SERVER_FOLDER)
        val spigotFileName = getJarName(version)
        return serverFolder.resolve(spigotFileName)
    }

    fun getJarName(
        version: SpigotVersion
    ): String = "spigot-${version}.jar"
}