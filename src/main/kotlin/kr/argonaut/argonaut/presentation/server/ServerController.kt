package kr.argonaut.argonaut.presentation.server

import kotlinx.coroutines.*
import kr.argonaut.argonaut.application.application.ApplicationService
import kr.argonaut.argonaut.application.server.ServerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServerController(
    private val serverService: ServerService,
    private val applicationService: ApplicationService,
) {
    @PostMapping("/reboot")
    fun reboot() {
        CoroutineScope(Dispatchers.Default).launch {
            serverService.reboot()
        }
    }
    @PostMapping("/shutdown")
    fun shutdown() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(100)
            applicationService.shutdown()
        }
    }
}