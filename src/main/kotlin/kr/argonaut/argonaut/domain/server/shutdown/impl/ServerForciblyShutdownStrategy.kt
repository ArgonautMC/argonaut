package kr.argonaut.argonaut.domain.server.shutdown.impl

import kr.argonaut.argonaut.domain.server.shutdown.ServerShutdownStrategy

class ServerForciblyShutdownStrategy: ServerShutdownStrategy {
    override suspend fun requestShutdownAndWait(process: Process) {
        process.destroyForcibly();
    }
}