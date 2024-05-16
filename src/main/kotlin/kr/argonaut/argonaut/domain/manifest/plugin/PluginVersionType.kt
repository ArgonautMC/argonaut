package kr.argonaut.argonaut.domain.manifest.plugin

enum class PluginVersionType(
    val typeChecker: (String) -> Boolean,
    val versionResolver: (String) -> PluginVersion
) {
    LATEST(
        { it == "latest" },
        { LatestPluginVersion },
    ),
    STANDARD(
        StandardPluginVersion::check,
        StandardPluginVersion::fromString,
    ),
}