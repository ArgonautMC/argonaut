package kr.argonaut.argonaut.domain.server

import kr.argonaut.argonaut.domain.server.process.ServerProcessManager
import kr.argonaut.argonaut.domain.server.process.ServerProcessService
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Component

@Component
class ConcurrentServerManager(
    private val serverProcessService: ServerProcessService,
): ServerManager {
    private val logger = getLogger()
    private var serverProcessManager: ServerProcessManager? = null

    override suspend fun run(force: Boolean, withLog: Boolean) {
        synchronized(this) {
            if (serverProcessManager != null) {
                if (force) throw IllegalStateException("Server already running")
                else return
            }
        }

        logger.info("running server (force: $force, withLog: $withLog)")
        this.serverProcessManager = serverProcessService.run(force, withLog)
    }

    override suspend fun stop(force: Boolean) {
        val onAlreadyStopped = { if (force) throw IllegalStateException("Server already stopped") }

        synchronized(this) {
            if (serverProcessManager == null)
                return onAlreadyStopped()
        }

        logger.info("Stopping server (force: $force)")
        serverProcessManager?.requestStopAndWait()
            ?: return onAlreadyStopped()

        serverProcessManager = null
        logger.info("The server has been successfully shut down.")
        logger.info("Saving data from the shutdown server.")
        serverProcessService.saveData()
        logger.info("The data has been successfully saved.")
    }
}