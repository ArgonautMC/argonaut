package kr.argonaut.argonaut.domain.server.shutdown.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import kotlin.time.Duration

class ServerGracefulShutdownStrategy(
    timeout: Duration,
): ServerTimeoutShutdownStrategy(timeout) {
    override suspend fun requestShutdown(process: Process) {
        val bufferedWriter = BufferedWriter(OutputStreamWriter(process.outputStream))
        val command = "stop"
        withContext(Dispatchers.IO) {
            bufferedWriter.write(command)
            bufferedWriter.newLine()
            bufferedWriter.flush()
        }
    }
}