package kr.argonaut.argonaut.domain.server.process

interface ServerProcessService {
    suspend fun run(force: Boolean, withLog: Boolean): ServerProcessManager
    suspend fun saveData()
}