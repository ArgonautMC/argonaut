package kr.argonaut.argonaut.domain.manifest.plugin

import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion

data class PluginVersion(
    val major: Int,
    val minor: Int,
    val patch: String,
) {
    override fun toString(): String {
        return "$major.$minor.$patch"
    }
    companion object {
        fun fromString(version: String): PluginVersion {
            try {
                val parts = version.split('.')
                return PluginVersion(
                    major = parts[0].toInt(),
                    minor = parts[1].toInt(),
                    patch = parts[2]
                )
            } catch (throwable: Throwable) {
                throw IllegalArgumentException("Malformed version: $version", throwable)
            }
        }
    }
}