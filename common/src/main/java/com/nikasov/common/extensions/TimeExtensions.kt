package com.nikasov.common.extensions

import java.time.Duration

fun Long.millisToReadableTimeFormat(): String {
    val duration = Duration.ofMillis(this)
    val minutes = duration.toMinutes()
    val seconds = duration.minusMinutes(minutes).seconds
    return "$minutes:${if (seconds > 9) "" else "0"}$seconds"
}