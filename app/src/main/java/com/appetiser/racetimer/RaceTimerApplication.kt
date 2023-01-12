package com.appetiser.racetimer

import android.app.Application
import com.appetiser.racetimer.local.RiderRepository
import com.appetiser.racetimer.local.RidersRoomDatabase

class RaceTimerApplication : Application(){

    val database by lazy { RidersRoomDatabase.getDatabase(this) }
    val repository by lazy { RiderRepository(database.riderDao()) }

    override fun onCreate() {
        super.onCreate()
        database.isOpen
    }
}