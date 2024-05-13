package kr.argonaut.argonaut.infra.spigot

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader
import kr.argonaut.argonaut.domain.spigot.SpigotManifest
import kr.argonaut.argonaut.domain.spigot.SpigotManifestLoader
import kr.argonaut.argonaut.lib.getLogger
import org.springframework.stereotype.Component

@Component
class SpigotManifestLoaderImpl(
    private val serverManifestLoader: ServerManifestLoader,
): SpigotManifestLoader {
    private val logger = getLogger()

    override suspend fun load(environment: Environment): SpigotManifest {
        logger.info(">load spigot manifest")

        val serverManifest = serverManifestLoader.load(environment)

        return SpigotManifest(
            version = serverManifest.spigotVersion,
        ).also { manifest ->
            logger.info("=spigotVersion: ${manifest.version}")
        }
    }
}