package co.touchlab.kampstarter

import kotlin.js.Date
import kotlin.math.roundToLong

internal actual fun printThrowable(t: Throwable) {
    // TODO
}

actual fun currentTimeMillis(): Long {
    return Date().getTime().roundToLong()
}
