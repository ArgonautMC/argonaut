package kr.argonaut.argonaut.domain.plugin.model

import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import java.nio.file.Path

data class PluginConfig(
    val bytesByPath: Map<Path, Array<Byte>>,
    val configManifest: PluginConfigManifest,
)