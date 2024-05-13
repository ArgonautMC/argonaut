package kr.argonaut.argonaut.domain.spigot

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.lib.HTTPUrlDownloader.downloadFile
import org.springframework.stereotype.Service

@Service
class SpigotService(
    private val spigotSource: SpigotSource,
    private val spigotRegistry: SpigotRegistry,
    private val spigotManifestLoader: SpigotManifestLoader,
    private val environment: Environment,
) {
    suspend fun loadJar() {
        val manifest = spigotManifestLoader.load(environment)
        if(!spigotSource.has(manifest.version)) downloadJar(manifest)
    }

    private fun downloadJar(manifest: SpigotManifest) {
        val downloadUrl = spigotRegistry.getDownloadUrl(manifest.version)
        val file = downloadFile(downloadUrl)
        spigotSource.save(manifest.version, file)
    }
}