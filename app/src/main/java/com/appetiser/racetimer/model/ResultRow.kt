package com.appetiser.racetimer.model

import com.appetiser.racetimer.ext.formatTime

class ResultRow(
    var rank: Int,
    var name: String = "Temp",
    var id: Int,
    var category: String  = "Hard Tail",
    var startTimeFormatted: String = "00:00.000",
    var finishTime: String = "00:00.000",
    var finishTimeFormatted: String = "00:00.000",
) {
    fun toList(): List<String> {
        return listOf<String>(
            rank.toString(),
            name,
            id.toString(),
            category,
            startTimeFormatted,
            finishTime,
            finishTimeFormatted,

        )
    }

}