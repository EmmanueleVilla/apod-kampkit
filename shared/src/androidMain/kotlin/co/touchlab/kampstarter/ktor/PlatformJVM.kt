package co.touchlab.kampstarter.ktor

actual suspend fun <R> network(block: suspend () -> R): R = block()
