package kr.argonaut.argonaut.domain.manifest.plugin

import com.fasterxml.jackson.annotation.JsonIgnore

data class PluginConfigManifest(
    val name: String,
) {
    @JsonIgnore
    val localFolderName: String = name

    @JsonIgnore
    val s3FolderName: String = name
}