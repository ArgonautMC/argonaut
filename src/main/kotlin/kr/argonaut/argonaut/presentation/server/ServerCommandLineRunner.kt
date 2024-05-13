package kr.argonaut.argonaut.presentation.server

import kotlinx.coroutines.runBlocking
import kr.argonaut.argonaut.domain.server.ServerManager
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ServerCommandLineRunner(
    private val serverManager: ServerManager
): CommandLineRunner {
    override fun run(vararg args: String?) {
        runBlocking {
            serverManager.run(force = false, withLog = true)
        }
    }
}