package kr.argonaut.argonaut.domain.spigot

import kr.argonaut.argonaut.domain.manifest.Environment

interface SpigotManifestLoader {
    suspend fun load(environment: Environment): SpigotManifest
}