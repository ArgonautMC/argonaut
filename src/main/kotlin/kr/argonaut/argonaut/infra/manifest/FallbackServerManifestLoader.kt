package kr.argonaut.argonaut.infra.manifest

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifest
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.lib.getLogger
import org.slf4j.Logger

class FallbackServerManifestLoader(
    val loader: ServerManifestLoader,
    val fallback: ServerManifestLoader,
    logger: Logger? = null,
): ServerManifestLoader {
    val logger = logger ?: getLogger()
    private val loaderName = loader::class.simpleName
    private val fallbackName = fallback::class.simpleName

    override suspend fun load(env: Environment): ServerManifest {
        try {
            logger.info("Attempting load server manifest via $loaderName.")
            return loader.load(env)
        } catch (e: Throwable) {
            logger.warn(
                "Failed to load server manifest via $loaderName." +
                " Attempting load via fallback strategy ($fallbackName).",
                e
            )
            return fallback.load(env)
        }
    }
}

fun <T: ServerManifestLoader> T.withFallback(
    fallback: ServerManifestLoader,
): ServerManifestLoader = FallbackServerManifestLoader(
    loader = this,
    fallback = fallback
)