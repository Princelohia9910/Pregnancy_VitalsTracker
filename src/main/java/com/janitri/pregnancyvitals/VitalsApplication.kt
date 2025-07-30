package com.janitri.pregnancyvitals

import android.app.Application
import com.janitri.pregnancyvitals.data.VitalsDatabase
import com.janitri.pregnancyvitals.repository.VitalsRepository

class VitalsApplication : Application() {
    val database by lazy { VitalsDatabase.getDatabase(this) }
    val repository by lazy { VitalsRepository(database.vitalsDao()) }
}
