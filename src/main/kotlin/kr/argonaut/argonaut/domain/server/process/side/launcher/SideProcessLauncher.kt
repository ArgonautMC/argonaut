package kr.argonaut.argonaut.domain.server.process.side.launcher

import kotlinx.coroutines.*

abstract class SideProcessLauncher {
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun launch(
        process: Process,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
    ): Job {
        return GlobalScope.launch(dispatcher) {
            launch(process)
        }
    }

    abstract suspend fun CoroutineScope.launch(process: Process)
}