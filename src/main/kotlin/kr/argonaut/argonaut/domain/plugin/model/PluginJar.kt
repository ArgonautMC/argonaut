package kr.argonaut.argonaut.domain.plugin.model

import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest

data class PluginJar(
    val bytes: Array<Byte>,
    val jarManifest: PluginJarManifest,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginJar

        if (!bytes.contentEquals(other.bytes)) return false
        if (jarManifest != other.jarManifest) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bytes.contentHashCode()
        result = 31 * result + jarManifest.hashCode()
        return result
    }
}
