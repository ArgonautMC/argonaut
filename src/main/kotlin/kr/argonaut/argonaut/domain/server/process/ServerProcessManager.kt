package kr.argonaut.argonaut.domain.server.process

import kotlinx.coroutines.*
import kr.argonaut.argonaut.domain.server.process.side.SideProcessManager
import kr.argonaut.argonaut.domain.server.process.side.SideProcessType
import kr.argonaut.argonaut.domain.server.shutdown.ServerShutdownStrategy
import java.io.File

class ServerProcessManager(
    serverFolder: File,
    jarFileName: String,
    sideProcesses: List<SideProcessType>,
    private val serverShutdownStrategy: ServerShutdownStrategy,
) {
    var status: ProcessStatus = ProcessStatus.UNLOADED
        private set
    private val process: Process
    private val sideProcessManager: SideProcessManager

    init {
        val (process, sideProcessManager) = runBlocking {
            val process = runProcess(serverFolder, jarFileName)
            val sideProcessManager = SideProcessManager(process)

            sideProcessManager.launchAll(sideProcesses)
            status = ProcessStatus.RUNNING

            process to sideProcessManager
        }

        CoroutineScope(Dispatchers.IO).launch {
            process.waitFor()
            synchronized(this@ServerProcessManager) {
                if(status != ProcessStatus.STOPPED_BY_REQUEST && status != ProcessStatus.STOPPING) {
                    status = ProcessStatus.STOPPED_BY_PROCESS
                }
            }
        }

        this.process = process
        this.sideProcessManager = sideProcessManager
    }

    suspend fun requestStopAndWait() {
        if(status == ProcessStatus.STOPPED_BY_PROCESS) return

        status = ProcessStatus.STOPPING
        serverShutdownStrategy.requestShutdownAndWait(process)
        sideProcessManager.cancelAndJoinAll()
        status = ProcessStatus.STOPPED_BY_REQUEST
    }
}

private suspend fun runProcess(
    serverFolder: File,
    jarFileName: String,
): Process {
    val jarPath = serverFolder.resolve(jarFileName).absolutePath

    val processBuilder = ProcessBuilder("java", "-jar", jarPath, "--nogui")
    processBuilder.directory(serverFolder)
    return withContext(Dispatchers.IO) {
        processBuilder.start()
    }
}