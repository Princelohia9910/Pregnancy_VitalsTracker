package com.janitri.pregnancyvitals.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.janitri.pregnancyvitals.ui.components.AddVitalsDialog
import com.janitri.pregnancyvitals.ui.components.EmptyStateScreen
import com.janitri.pregnancyvitals.ui.components.VitalsCard
import com.janitri.pregnancyvitals.viewmodel.VitalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsScreen(viewModel: VitalsViewModel) {
    val vitals by viewModel.vitals.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pregnancy Vitals Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddDialog() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Vitals")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (vitals.isEmpty()) {
                EmptyStateScreen()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(vitals) { vital ->
                        VitalsCard(
                            vital = vital,
                            onDelete = { viewModel.deleteVitals(vital) }
                        )
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (showDialog) {
            AddVitalsDialog(
                onDismiss = { viewModel.hideDialog() },
                onSubmit = { systolic, diastolic, weight, kicks ->
                    viewModel.addVitals(systolic, diastolic, weight, kicks)
                }
            )
        }
    }
}
