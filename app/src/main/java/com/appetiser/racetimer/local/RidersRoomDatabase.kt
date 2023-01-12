package com.appetiser.racetimer.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appetiser.racetimer.model.Rider


@Database(entities = arrayOf(Rider::class), version = 1, exportSchema = false)
public abstract class RidersRoomDatabase : RoomDatabase() {

    abstract fun riderDao(): RiderDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RidersRoomDatabase? = null

        fun getDatabase(context: Context): RidersRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RidersRoomDatabase::class.java,
                    "rider_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}