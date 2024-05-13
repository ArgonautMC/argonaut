package kr.argonaut.argonaut.domain.spigot

import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion

interface SpigotSource {
    fun has(version: SpigotVersion): Boolean
    fun save(version: SpigotVersion, data: Array<Byte>)
}