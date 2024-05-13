package kr.argonaut.argonaut.infra.plugin

import kr.argonaut.argonaut.domain.manifest.Environment
import kr.argonaut.argonaut.domain.plugin.RemotePluginSource
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.infra.s3.S3Service
import kr.argonaut.argonaut.lib.mapAsync
import org.springframework.stereotype.Component

@Component
class S3RemotePluginSource(
    private val s3Service: S3Service,
    env: Environment,
): RemotePluginSource {
    private val pluginConfigBucketId = "argonaut-$env--plugin-configs"

    override suspend fun add(config: PluginConfig) {
        config.bytesByPath.entries.mapAsync { (path, bytes) ->
            s3Service.putObject(pluginConfigBucketId, "$path", bytes.toByteArray())
        }
    }
}