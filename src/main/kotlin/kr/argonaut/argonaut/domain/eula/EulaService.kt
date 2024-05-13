package kr.argonaut.argonaut.domain.eula

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.argonaut.argonaut.domain.workspace.WorkspaceConfiguration
import kr.argonaut.argonaut.domain.workspace.WorkspacePaths
import kr.argonaut.argonaut.lib.use
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.FileOutputStream

@Service
class EulaService(
    workspaceConfiguration: WorkspaceConfiguration,
) {
    val workspaceEulaFile = workspaceConfiguration.getWorkspace()
        .resolve(WorkspacePaths.EULA_FILE)

    suspend fun load() {
        workspaceEulaFile.parentFile.mkdirs()
        if(workspaceEulaFile.exists()) workspaceEulaFile.delete()

        val resource = ClassPathResource("eula.txt")

        val input = resource.inputStream
        val output = withContext(Dispatchers.IO) {
            FileOutputStream(workspaceEulaFile)
        }

        use(input, output) { inputStream, outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
}