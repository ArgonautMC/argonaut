package kr.argonaut.argonaut.application.server

import kr.argonaut.argonaut.domain.server.ServerManager
import org.springframework.stereotype.Service

@Service
class ServerService(
    private val serverManager: ServerManager,
) {
    suspend fun reboot() {
        serverManager.stop()
        serverManager.run()
    }
}