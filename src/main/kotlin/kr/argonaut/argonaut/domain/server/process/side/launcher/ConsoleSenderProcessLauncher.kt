package kr.argonaut.argonaut.domain.server.process.side.launcher

import kotlinx.coroutines.*
import kr.argonaut.argonaut.lib.use
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.*

class ConsoleSenderProcessLauncher: SideProcessLauncher() {
    override suspend fun CoroutineScope.launch(process: Process) {
        val bufferedWriter = BufferedWriter(OutputStreamWriter(process.outputStream))
        val s = Scanner(System.`in`)
        use(bufferedWriter, s) { writer, scanner ->
            while (isActive && process.isAlive &&
                scanner.hasNextLine()
            ) {
                val line = scanner.nextLine()
                withContext(Dispatchers.IO) {
                    writer.write(line)
                    writer.newLine()
                    writer.flush()
                }
            }
        }
    }
}