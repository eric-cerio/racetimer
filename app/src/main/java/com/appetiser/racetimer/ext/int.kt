package com.appetiser.racetimer.ext

import android.content.Context
import java.util.concurrent.TimeUnit

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Long.formatTime(): String {
    return String.format(
        "%02d:%02d.%03d",
        TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(this)
        ),
        TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                this
            )
        ),
        this - TimeUnit.SECONDS.toMillis(
            TimeUnit.MILLISECONDS.toSeconds(this)
        )
    )
}