package com.janitri.pregnancyvitals.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.janitri.pregnancyvitals.data.VitalsEntity
import com.janitri.pregnancyvitals.ui.components.AddVitalsDialog
import com.janitri.pregnancyvitals.ui.components.VitalsCard
import com.janitri.pregnancyvitals.viewmodel.VitalsViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: VitalsViewModel) {
    val vitals by viewModel.vitals.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pregnancy Vitals Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddDialog() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Vitals"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                if (vitals.isEmpty()) {
                    Text(
                        text = "No vitals recorded yet. Tap the + button to add your first entry.",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(vitals) { vital ->
                            VitalsCard(
                                vital = vital,
                                onDelete = { viewModel.deleteVitals(vital) }
                            )
                        }
                    }
                }
            }

            if (showDialog) {
                AddVitalsDialog(
                    onDismiss = { viewModel.hideDialog() },
                    onSubmit = { systolic, diastolic, weight, kicks ->
                        viewModel.addVitals(
                            systolic = systolic,
                            diastolic = diastolic,
                            weight = weight,
                            babyKicks = kicks
                        )
                    }
                )
            }
        }
    }
}
