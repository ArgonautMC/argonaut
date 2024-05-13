package kr.argonaut.argonaut.domain.server.shutdown.impl

import kr.argonaut.argonaut.domain.server.shutdown.ServerShutdownStrategy
import kr.argonaut.argonaut.lib.getLogger
import org.slf4j.Logger

class FallbackServerShutdownStrategy(
    private val strategy: ServerShutdownStrategy,
    private val fallback: ServerShutdownStrategy,
    logger: Logger? = null
): ServerShutdownStrategy {
    private val logger = logger ?: getLogger()
    private val strategyName = strategy::class.simpleName
    private val fallbackName = fallback::class.simpleName

    override suspend fun requestShutdownAndWait(process: Process) {
        try {
            logger.info("Attempting server shutdown via $strategyName.")
            strategy.requestShutdownAndWait(process)
        } catch (e: Exception) {
            logger.warn(
                "Failed to shut down the server via $strategyName." +
                " Attempting shutdown via fallback strategy ($fallbackName).",
                e
            )
            fallback.requestShutdownAndWait(process)
        }
    }
}

fun <T: ServerShutdownStrategy> T.withFallback(
    fallback: ServerShutdownStrategy,
): ServerShutdownStrategy = FallbackServerShutdownStrategy(
    strategy = this,
    fallback = fallback
)