package com.janitri.pregnancyvitals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.janitri.pregnancyvitals.data.VitalsEntity
import com.janitri.pregnancyvitals.repository.VitalsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for managing pregnancy vitals data and UI state.
 * This class serves as a bridge between the UI and data layers.
 *
 * @param repository The repository that provides access to vitals data
 */
class VitalsViewModel(private val repository: VitalsRepository) : ViewModel() {

    // Private MutableStateFlow (can be changed) for vitals list
    private val _vitals = MutableStateFlow<List<VitalsEntity>>(emptyList())

    // Public StateFlow (read-only) exposed to the UI
    val vitals: StateFlow<List<VitalsEntity>> = _vitals.asStateFlow()

    // Controls whether to show the add vitals dialog
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    // Controls loading state for operations
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * Initialize the ViewModel by loading vitals data from the repository
     */
    init {
        // Launch a coroutine in the viewModelScope
        viewModelScope.launch {
            // Collect vitals from repository and update the _vitals state
            repository.getAllVitals().collect { vitalsList ->
                _vitals.value = vitalsList
            }
        }
    }

    /**
     * Show the add vitals dialog
     */
    fun showAddDialog() {
        _showDialog.value = true
    }

    /**
     * Hide the add vitals dialog
     */
    fun hideDialog() {
        _showDialog.value = false
    }

    /**
     * Add a new vitals record to the database
     *
     * @param systolic Systolic blood pressure value
     * @param diastolic Diastolic blood pressure value
     * @param weight Weight in kilograms
     * @param babyKicks Number of baby kicks counted
     */
    fun addVitals(
        systolic: Int,
        diastolic: Int,
        weight: Float,
        babyKicks: Int
    ) {
        // Launch a coroutine to perform database operation
        viewModelScope.launch {
            // Show loading indicator
            _isLoading.value = true

            try {
                // Create a new VitalsEntity object
                val newVitals = VitalsEntity(
                    systolicBP = systolic,
                    diastolicBP = diastolic,
                    weight = weight,
                    babyKicks = babyKicks
                )

                // Insert the new vitals record into the database
                repository.insertVitals(newVitals)

                // Hide the dialog after successful addition
                _showDialog.value = false
            } catch (e: Exception) {
                // Handle errors (could add error messaging in a more advanced app)
                println("Error adding vitals: ${e.message}")
            } finally {
                // Hide loading indicator when operation completes
                _isLoading.value = false
            }
        }
    }

    /**
     * Delete a vitals record from the database
     *
     * @param vitals The vitals record to delete
     */
    fun deleteVitals(vitals: VitalsEntity) {
        viewModelScope.launch {
            repository.deleteVitals(vitals)
        }
    }
}

/**
 * Factory class for creating VitalsViewModel instances with dependencies.
 * This is needed because VitalsViewModel requires a repository parameter.
 *
 * @param repository The repository to be injected into the ViewModel
 */
class VitalsViewModelFactory(private val repository: VitalsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VitalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VitalsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
