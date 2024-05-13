package kr.argonaut.argonaut.domain.server

interface ServerManager {
    suspend fun run(force: Boolean = false, withLog: Boolean = true)
    suspend fun stop(force: Boolean = false)
}