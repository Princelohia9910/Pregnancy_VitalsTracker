package com.janitri.pregnancyvitals.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {
    @Query("SELECT * FROM vitals ORDER BY timestamp DESC")
    fun getAllVitals(): Flow<List<VitalsEntity>>

    @Insert
    suspend fun insertVitals(vitals: VitalsEntity)

    @Delete
    suspend fun deleteVitals(vitals: VitalsEntity)

    @Query("DELETE FROM vitals WHERE id = :id")
    suspend fun deleteVitalsById(id: Long)
}
