package com.janitri.pregnancyvitals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddVitalsDialog(
    onDismiss: () -> Unit,
    onSubmit: (systolic: Int, diastolic: Int, weight: Float, kicks: Int) -> Unit
) {
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }

    var weight by remember { mutableStateOf("") }
    var babyKicks by remember { mutableStateOf("") }

    val isValid = systolic.isNotBlank() && diastolic.isNotBlank() &&
                  weight.isNotBlank() && babyKicks.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add New Vital Measurements",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Blood Pressure",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = systolic,
                        onValueChange = { if (it.all { char -> char.isDigit() }) systolic = it },
                        label = { Text("Systolic") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        supportingText = { Text("Top number") }
                    )

                    OutlinedTextField(
                        value = diastolic,
                        onValueChange = { if (it.all { char -> char.isDigit() }) diastolic = it },
                        label = { Text("Diastolic") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        supportingText = { Text("Bottom number") }
                    )
                }



                OutlinedTextField(
                    value = weight,
                    onValueChange = { input ->
                        if (input.matches(Regex("^\\d*\\.?\\d*$"))) weight = input
                    },
                    label = { Text("Weight") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("In kilograms (kg)") }
                )

                OutlinedTextField(
                    value = babyKicks,
                    onValueChange = { if (it.all { char -> char.isDigit() }) babyKicks = it },
                    label = { Text("Baby Kicks") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("Number of kicks counted") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isValid) {
                        onSubmit(
                            systolic.toInt(),
                            diastolic.toInt(),
                            weight.toFloat(),
                            babyKicks.toInt()
                        )
                    }
                },
                enabled = isValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
