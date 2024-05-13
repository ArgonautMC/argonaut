package kr.argonaut.argonaut.infra.spigot

import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion
import kr.argonaut.argonaut.domain.spigot.SpigotRegistry
import org.springframework.stereotype.Service
import java.net.MalformedURLException
import java.net.URL

@Service
class GetBukkitSpigotRegistry: SpigotRegistry {
    override fun getDownloadUrl(version: SpigotVersion): URL {
        try {
            val v = version.toString()
            return "https://download.getbukkit.org/spigot/spigot-$v.jar"
                .let(::URL)
        } catch (e: MalformedURLException) {
            throw IllegalArgumentException("malformed spigot version - $version", e)
        }
    }
}