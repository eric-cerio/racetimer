package com.appetiser.racetimer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.concurrent.TimeUnit

@Entity(tableName = "riders")
data class Rider(
    @PrimaryKey @ColumnInfo(name = "id")
    var id: Int,
    var categoryId: Int,
    var name: String = "Temp",
    var category: String  = "Hard Tail",
    var startTimeFormatted: String = "00:00.000",
    var elapseTime: Long = 0L,
    var finishTime: Long = 0L,
    var finishTimeFormatted: String = "00:00.000",
    var runType: String = "seeding",
    var seedingTime: Long = 0L,
    var finalTime: Long = 0L
) {
    override fun toString(): String {
        return "$id - $finishTime"
    }
}

