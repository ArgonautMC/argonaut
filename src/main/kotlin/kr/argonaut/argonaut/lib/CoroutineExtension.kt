package kr.argonaut.argonaut.lib

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun <A, B> Iterable<A>.mapAsync(
    f: suspend (A) -> B
): List<Deferred<B>> = coroutineScope {
    map { async { f(it) } }
}
