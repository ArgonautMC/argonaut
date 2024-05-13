package kr.argonaut.argonaut

import kr.argonaut.argonaut.domain.manifest.Environment
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
@EnableConfigurationProperties
class ArgonautApplication {
    @Bean
    fun spigotEnvironment(@Value("\${spring.profiles.active}") profile: String): Environment =
        Environment.fromString(profile)
}

fun main(args: Array<String>) {
    runApplication<ArgonautApplication>(*args)
}
