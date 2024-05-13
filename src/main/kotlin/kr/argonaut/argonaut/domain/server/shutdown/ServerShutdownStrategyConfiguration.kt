package kr.argonaut.argonaut.domain.server.shutdown

import kr.argonaut.argonaut.domain.server.shutdown.impl.ServerDefaultShutdownStrategy
import kr.argonaut.argonaut.domain.server.shutdown.impl.ServerForciblyShutdownStrategy
import kr.argonaut.argonaut.domain.server.shutdown.impl.ServerGracefulShutdownStrategy
import kr.argonaut.argonaut.domain.server.shutdown.impl.withFallback
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Configuration
class ServerShutdownStrategyConfiguration {
    @Bean
    fun serverShutdownStrategy(): ServerShutdownStrategy =
        ServerGracefulShutdownStrategy(1.minutes)
            .withFallback(ServerDefaultShutdownStrategy(10.seconds))
            .withFallback(ServerForciblyShutdownStrategy())
}