package kr.argonaut.argonaut.domain.plugin

import kr.argonaut.argonaut.domain.plugin.model.PluginConfig

interface PluginSource {
    suspend fun add(config: PluginConfig)
}
