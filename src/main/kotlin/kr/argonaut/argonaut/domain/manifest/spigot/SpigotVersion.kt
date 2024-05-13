package kr.argonaut.argonaut.domain.manifest.spigot

data class  SpigotVersion(
    val major: Int,
    val minor: Int,
    val patch: String,
) {
    override fun toString(): String = "$major.$minor.$patch"
    companion object {
        fun fromString(version: String): SpigotVersion {
            try {
                val parts = version.split('.')
                return SpigotVersion(
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