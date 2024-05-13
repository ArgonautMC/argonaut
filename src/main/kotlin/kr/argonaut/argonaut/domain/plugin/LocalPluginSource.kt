package kr.argonaut.argonaut.domain.plugin

import kr.argonaut.argonaut.domain.plugin.model.PluginJar

interface LocalPluginSource: PluginSource {
    suspend fun removeAll(
        onlyJar: Boolean = true,
        vararg excludes: String = emptyArray(),
    ): Int
    suspend fun add(plugin: PluginJar)
}