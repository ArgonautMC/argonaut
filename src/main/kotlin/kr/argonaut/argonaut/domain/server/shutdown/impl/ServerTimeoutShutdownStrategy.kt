package kr.argonaut.argonaut.domain.server.shutdown.impl

import kotlinx.coroutines.withTimeout
import kr.argonaut.argonaut.domain.server.shutdown.ServerShutdownStrategy
import kotlin.time.Duration

abstract class ServerTimeoutShutdownStrategy(
    private val timeout: Duration,
): ServerShutdownStrategy {
    final override suspend fun requestShutdownAndWait(process: Process) {
        requestShutdown(process)
        withTimeout(timeout.inWholeMilliseconds) {
            process.waitFor()
        }
    }

    abstract suspend fun requestShutdown(process: Process)
}