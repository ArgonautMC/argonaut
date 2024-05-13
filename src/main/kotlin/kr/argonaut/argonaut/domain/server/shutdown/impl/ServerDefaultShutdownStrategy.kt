package kr.argonaut.argonaut.domain.server.shutdown.impl

import kotlin.time.Duration

class ServerDefaultShutdownStrategy(
    timeout: Duration,
): ServerTimeoutShutdownStrategy(timeout) {
    override suspend fun requestShutdown(process: Process) {
        process.destroy()
    }
}