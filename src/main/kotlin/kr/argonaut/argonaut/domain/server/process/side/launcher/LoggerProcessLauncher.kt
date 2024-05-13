package kr.argonaut.argonaut.domain.server.process.side.launcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import java.io.BufferedReader
import java.io.InputStreamReader

class LoggerProcessLauncher: SideProcessLauncher() {
    override suspend fun CoroutineScope.launch(process: Process) {
        val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
        var line = ""
        bufferedReader.use { reader ->
            while (isActive && process.isAlive &&
                reader.readLine()
                    ?.also { line = it } != null
            ) {
                println(line)
            }
        }
    }
}
