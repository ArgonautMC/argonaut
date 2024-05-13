package kr.argonaut.argonaut.application.application

import kr.argonaut.argonaut.domain.server.ServerManager
import org.springframework.stereotype.Service
import kotlin.system.exitProcess

@Service
class ApplicationService(
    private val serverManager: ServerManager
) {
    suspend fun shutdown() {
        serverManager.stop()
        exitProcess(0)
    }
}