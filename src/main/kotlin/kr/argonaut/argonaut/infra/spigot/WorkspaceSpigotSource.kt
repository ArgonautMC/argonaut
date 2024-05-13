package kr.argonaut.argonaut.infra.spigot

import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion
import kr.argonaut.argonaut.domain.spigot.SpigotJarPathStrategy
import kr.argonaut.argonaut.domain.spigot.SpigotSource
import kr.argonaut.argonaut.domain.workspace.WorkspaceConfiguration
import kr.argonaut.argonaut.domain.workspace.WorkspacePaths
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream

@Component
class WorkspaceSpigotSource(
    private val workspaceConfiguration: WorkspaceConfiguration,
): SpigotSource {
    override fun has(version: SpigotVersion): Boolean =
        getJarFile(version).exists()

    override fun save(version: SpigotVersion, data: Array<Byte>) {
        val file = getJarFile(version)
        if(file.exists()) file.delete()
        file.createNewFile()

        val outputStream = FileOutputStream(file)
        outputStream.write(data.toByteArray())
        outputStream.close()
    }

    private fun getJarFile(version: SpigotVersion): File =
        SpigotJarPathStrategy.getJarFile(
            workspaceConfiguration,
            version
        )
}