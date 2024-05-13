package kr.argonaut.argonaut.domain.server.process.side

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kr.argonaut.argonaut.domain.server.process.side.SideProcessLauncherConfiguration.SIDE_PROCESS_LAUNCHER
import kr.argonaut.argonaut.lib.all
import kr.argonaut.argonaut.lib.getLogger

class SideProcessManager(
    private val process: Process
) {
    private val logger = getLogger()
    private val sideProcessors: MutableMap<SideProcessType, Job> = HashMap(SideProcessType.entries.size)

    suspend fun launchAll(sideProcesses: List<SideProcessType>): Result<Any> =
        sideProcesses.map { type -> launch(type) }.all()

    suspend fun cancelAndJoinAll() {
        sideProcessors.entries.forEach { (type, job) ->
            logger.info("Cancel '$type' process")
            job.cancelAndJoin()
        }
    }

    private suspend fun launch(
        type: SideProcessType,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
    ): Result<Unit> {
        val launcher = requireNotNull(SIDE_PROCESS_LAUNCHER[type]) {
            "'$type' process launcher not registered"
        }

        if(sideProcessors.containsKey(type))
            return Result.failure(
                IllegalStateException("$type process is already launched")
            )
        sideProcessors[type] = launcher.launch(process, dispatcher)
        return Result.success(Unit)
    }
}