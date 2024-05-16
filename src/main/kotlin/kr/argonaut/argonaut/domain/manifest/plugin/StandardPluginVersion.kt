package kr.argonaut.argonaut.domain.manifest.plugin

data class StandardPluginVersion(
    private val major: String,
    private val minor: String,
    private val patch: String,
): PluginVersion("$major.$minor.$patch") {
    companion object {
        fun check(version: String): Boolean =
            Regex("^\\d+\\.\\d+\\.\\w+\$").matches(version)

        fun fromString(version: String): PluginVersion {
            try {
                val parts = version.split('.')
                return StandardPluginVersion(
                    major = parts[0],
                    minor = parts[1],
                    patch = parts[2]
                )
            } catch (throwable: Throwable) {
                throw IllegalArgumentException("Malformed version: $version", throwable)
            }
        }
    }
}