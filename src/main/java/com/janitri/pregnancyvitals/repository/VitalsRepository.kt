package com.janitri.pregnancyvitals.repository

import com.janitri.pregnancyvitals.data.VitalsDao
import com.janitri.pregnancyvitals.data.VitalsEntity
import kotlinx.coroutines.flow.Flow

class VitalsRepository(private val vitalsDao: VitalsDao) {
    fun getAllVitals(): Flow<List<VitalsEntity>> = vitalsDao.getAllVitals()

    suspend fun insertVitals(vitals: VitalsEntity) {
        vitalsDao.insertVitals(vitals)
    }

    suspend fun deleteVitals(vitals: VitalsEntity) {
        vitalsDao.deleteVitals(vitals)
    }

    suspend fun deleteVitalsById(id: Long) {
        vitalsDao.deleteVitalsById(id)
    }
}
