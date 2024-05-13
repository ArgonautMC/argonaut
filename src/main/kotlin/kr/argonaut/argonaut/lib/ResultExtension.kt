package kr.argonaut.argonaut.lib

fun <T> List<Result<T>>.all(): Result<List<T>> =
    if (any { it.isFailure }) Result.failure(Throwable("A result has errors"))
    else Result.success(map { it.getOrNull()!! })