package com.janitri.pregnancyvitals.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val systolicBP: Int,
    val diastolicBP: Int,
    val weight: Float,
    val babyKicks: Int,
    val timestamp: Long = System.currentTimeMillis()
)
