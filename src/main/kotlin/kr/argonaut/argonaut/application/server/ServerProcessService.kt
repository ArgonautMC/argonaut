package kr.argonaut.argonaut.application.server

import kr.argonaut.argonaut.domain.eula.EulaService
import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.domain.plugin.PluginDownloadScript
import kr.argonaut.argonaut.domain.plugin.PluginUploadScript
import kr.argonaut.argonaut.domain.server.process.ServerProcessManager
import kr.argonaut.argonaut.domain.server.process.ServerProcessManagerFactory
import kr.argonaut.argonaut.domain.server.process.ServerProcessService
import kr.argonaut.argonaut.domain.server.process.side.SideProcessType
import kr.argonaut.argonaut.domain.server.process.side.SideProcessType.LOGGER
import kr.argonaut.argonaut.domain.spigot.SpigotJarPathStrategy.getJarName
import kr.argonaut.argonaut.domain.spigot.SpigotService
import kr.argonaut.argonaut.domain.workspace.WorkspaceConfiguration
import kr.argonaut.argonaut.domain.workspace.WorkspacePaths
import org.springframework.stereotype.Service
import java.io.File

@Service
class ServerProcessService(
    private val pluginDownloadScript: PluginDownloadScript,
    private val pluginUploadScript: PluginUploadScript,

    private val serverProcessManagerFactory: ServerProcessManagerFactory,
    private val serverManifestLoader: ServerManifestLoader,

    private val spigotService: SpigotService,
    private val eulaService: EulaService,
    private val workspaceConfiguration: WorkspaceConfiguration,
    private val environment: Environment,
): ServerProcessService {
    override suspend fun run(force: Boolean, withLog: Boolean): ServerProcessManager {
        loadDependencies()
        val sideProcesses = loadSideProcesses(withLog)
        val serverProcessManager = loadProcessManager(sideProcesses)

        return serverProcessManager
    }

    override suspend fun saveData() {
        pluginUploadScript.upload()
    }

    private suspend fun loadDependencies() {
        spigotService.loadJar()
        eulaService.load()
        pluginDownloadScript.download()
    }

    private fun  loadSideProcesses(withLog: Boolean) = listOfNotNull(
        LOGGER.takeIf { withLog },
    )

    private suspend fun loadProcessManager(
        sideProcesses: List<SideProcessType>,
    ): ServerProcessManager {
        val manifest = serverManifestLoader.load(environment)
        val jarName = getJarName(manifest.spigotVersion)
        val serverFolder = getServerFolder()

        return serverProcessManagerFactory.create(
            serverFolder,
            jarName,
            sideProcesses,
        )
    }

    private fun getServerFolder(): File =
        workspaceConfiguration.getWorkspace()
            .resolve(WorkspacePaths.SERVER_FOLDER)
}