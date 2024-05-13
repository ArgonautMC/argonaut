package kr.argonaut.argonaut.domain.spigot

import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion
import java.net.URL

interface SpigotRegistry {
    fun getDownloadUrl(version: SpigotVersion): URL
}