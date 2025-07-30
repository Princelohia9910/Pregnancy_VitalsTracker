package com.janitri.pregnancyvitals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.janitri.pregnancyvitals.ui.screen.MainScreen
import com.janitri.pregnancyvitals.ui.theme.PregnancyVitalsTrackerTheme
import com.janitri.pregnancyvitals.utils.ReminderScheduler
import com.janitri.pregnancyvitals.viewmodel.VitalsViewModel
import com.janitri.pregnancyvitals.viewmodel.VitalsViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var vitalsViewModel: VitalsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setupViewModel()
        ReminderScheduler.scheduleReminder(this)

        setContent {
            PregnancyVitalsTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(vitalsViewModel)
                }
            }
        }
    }

    private fun setupViewModel() {
        val application = application as VitalsApplication
        val repository = application.repository
        val viewModelFactory = VitalsViewModelFactory(repository)
        vitalsViewModel = ViewModelProvider(this, viewModelFactory)[VitalsViewModel::class.java]
    }
}
