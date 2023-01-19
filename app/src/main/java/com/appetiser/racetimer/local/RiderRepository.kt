package com.appetiser.racetimer.local

import android.util.Log
import androidx.annotation.WorkerThread
import com.appetiser.racetimer.model.Rider
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.util.concurrent.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class RiderRepository(private val riderDao: RiderDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allRider: Single<List<Rider>> = riderDao.getAllRiders()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(rider: Rider): Completable {
        return riderDao.insert(rider)
    }

    fun getRider(id: Int): Single<Rider> {
        return riderDao.getRider(id)
    }

    fun insertAllRiders(list: List<Rider>): Completable {
        return riderDao.insertAll(list)
    }

    fun parseCSV(file: File): Single<List<Rider>> {
        return Single.just(file)
            .flatMap {
                Single.just(csvReader().readAll(it))
            }
            .map {
                Log.e("Riders count", "${it.size}")
                Log.e("Riders", it.toString())

                it.map {
                    Rider(
                        id = it[0].toString().toInt(),
                        name = it[1].toString(),
                        category = it[2].toString(),
                        categoryId = it[4].toInt()
                    )
                }
            }
    }

    fun deleteAll(): Completable {
        return riderDao.deleteAll()
    }
}