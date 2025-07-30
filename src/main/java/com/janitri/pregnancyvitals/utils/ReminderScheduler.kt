package com.janitri.pregnancyvitals.utils

import android.content.Context
import androidx.work.*
import com.janitri.pregnancyvitals.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun scheduleReminder(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            5, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresBatteryNotLow(false)
                .build()
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "vitals_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun cancelReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("vitals_reminder")
    }
}
