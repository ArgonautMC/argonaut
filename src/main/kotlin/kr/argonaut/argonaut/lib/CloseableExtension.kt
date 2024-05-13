package kr.argonaut.argonaut.lib

import java.io.Closeable

inline fun <T1 : Closeable?, T2: Closeable?, R> use(t1: T1, t2: T2, block: (T1, T2) -> R): R {
    t1.use { a ->
        t2.use { b ->
            return block(a, b)
        }
    }
}