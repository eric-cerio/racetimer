package com.appetiser.racetimer.model

import java.util.concurrent.TimeUnit


class Rider(
    var id: Int,
    var startTime: String,
    var elapseTime: Long,
    var finishTime: Long
) {
    override fun toString(): String {
        return "$id - $finishTime"
    }

}

