package com.appetiser.racetimer.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.appetiser.racetimer.model.Rider
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RiderDao {

    @Query("SELECT * FROM riders")
    fun getAllRiders(): Single<List<Rider>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(rider: Rider): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(riders: List<Rider>): Completable

    @Update
    fun update(rider: Rider): Completable

    @Update
    fun updateAllRiders(riders: List<Rider>): Completable

    @Query("DELETE FROM riders")
    fun deleteAll(): Completable

    @Query("SELECT * FROM  riders WHERE id=:id")
    fun getRider(id: Int): Single<Rider>
}