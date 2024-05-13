package kr.argonaut.argonaut.infra.manifest

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.manifest.ServerManifest
import kr.argonaut.argonaut.domain.manifest.ServerManifestLoader

class ApplicationCachedServerManifestLoader(
    private val serverManifestLoader: ServerManifestLoader,
): ServerManifestLoader {
    lateinit var manifest: ServerManifest

    override suspend fun load(env: Environment): ServerManifest {
        if(!this::manifest.isInitialized)
            manifest = serverManifestLoader.load(env)
        return manifest
    }
}