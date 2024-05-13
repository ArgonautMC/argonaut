package kr.argonaut.argonaut.domain.server.process

import kr.argonaut.argonaut.domain.server.process.side.SideProcessType
import kr.argonaut.argonaut.domain.server.shutdown.ServerShutdownStrategy
import org.springframework.stereotype.Component
import java.io.File

@Component
class ServerProcessManagerFactory(
    private val serverShutdownStrategy: ServerShutdownStrategy
) {
    fun create(
        serverFolder: File,
        jarFileName: String,
        sideProcesses: List<SideProcessType>
    ): ServerProcessManager {
        return ServerProcessManager(serverFolder, jarFileName, sideProcesses, serverShutdownStrategy)
    }
}