package kr.argonaut.argonaut.domain.server.process.side

import kr.argonaut.argonaut.domain.server.process.side.launcher.ConsoleSenderProcessLauncher
import kr.argonaut.argonaut.domain.server.process.side.launcher.LoggerProcessLauncher
import kr.argonaut.argonaut.domain.server.process.side.launcher.SideProcessLauncher

object SideProcessLauncherConfiguration {
    val SIDE_PROCESS_LAUNCHER: Map<SideProcessType, SideProcessLauncher> = mapOf(
        SideProcessType.LOGGER to LoggerProcessLauncher(),
        SideProcessType.CONSOLE_SENDER to ConsoleSenderProcessLauncher(),
    )
}