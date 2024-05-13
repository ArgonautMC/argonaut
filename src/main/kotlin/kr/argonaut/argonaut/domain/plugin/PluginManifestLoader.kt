package kr.argonaut.argonaut.domain.plugin

import kr.argonaut.argonaut.domain.plugin.model.PluginManifests
import kr.argonaut.argonaut.domain.manifest.Environment

interface PluginManifestLoader {
    suspend fun loadAll(env: Environment): PluginManifests
}