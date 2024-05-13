package kr.argonaut.argonaut.domain.server.process

enum class ProcessStatus {
    UNLOADED, RUNNING, STOPPING, STOPPED_BY_REQUEST, STOPPED_BY_PROCESS
}