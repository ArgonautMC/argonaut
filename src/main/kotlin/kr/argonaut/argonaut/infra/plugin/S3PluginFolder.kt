package kr.argonaut.argonaut.infra.plugin

import kr.argonaut.argonaut.domain.manifest.plugin.PluginVersion

data class S3PluginFolder(
    val pluginJarKey: String,
    val pluginConfigFolderKey: String
) {
    constructor(
        key: String,
        version: PluginVersion
    ): this(
        "$key/$version.jar",
        "$key/config"
    )
}