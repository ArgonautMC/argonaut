package kr.argonaut.argonaut.domain.manifest.plugin

abstract class PluginVersion(
    private val version: String
){
    final override fun toString(): String = version

    companion object {
        fun fromString(version: String): PluginVersion =
            PluginVersionType.entries
                .find { it.typeChecker(version) }
                ?.let { it.versionResolver(version) }
                ?: throw IllegalArgumentException("malformed version - $version")
    }
}