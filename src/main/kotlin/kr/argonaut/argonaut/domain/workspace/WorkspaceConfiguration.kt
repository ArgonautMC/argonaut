package kr.argonaut.argonaut.domain.workspace

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Paths
import javax.annotation.PostConstruct
import kotlin.io.path.absolutePathString

@Component
class WorkspaceConfiguration(
    @Value("\${workspace.path}")
    path: String,
) {
    private val path = path.takeIf { it.isNotBlank() }
        ?: Paths.get("").absolutePathString()

    fun getWorkspace(): File = File(path)

    @Suppress("FunctionName")
    @PostConstruct
    fun `#onConstructed`() {
        try {
            File(path).mkdirs()
            File(path).resolve(WorkspacePaths.SERVER_FOLDER).mkdirs()
            File(path).resolve(WorkspacePaths.PLUGIN_FOLDER).mkdirs()
        } catch (e: Exception) {
            throw IllegalArgumentException("malformed workspace path", e)
        }
    }
}