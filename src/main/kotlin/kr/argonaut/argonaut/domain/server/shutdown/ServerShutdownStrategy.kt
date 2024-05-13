package kr.argonaut.argonaut.domain.server.shutdown

interface ServerShutdownStrategy {
    suspend fun requestShutdownAndWait(process: Process)
}