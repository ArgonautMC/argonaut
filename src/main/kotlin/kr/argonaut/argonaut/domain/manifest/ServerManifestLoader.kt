package kr.argonaut.argonaut.domain.manifest

interface ServerManifestLoader {
    suspend fun load(env: Environment): ServerManifest
}