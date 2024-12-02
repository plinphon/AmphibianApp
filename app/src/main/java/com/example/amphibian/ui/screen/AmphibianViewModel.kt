package com.example.amphibian.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibian.AmphibianApplication
import com.example.amphibian.data.AmphibianRepository
import com.example.amphibian.network.Amphibian
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AmphibianUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibianUiState
    data class Error(val message: String) : AmphibianUiState
    object Loading : AmphibianUiState
}

class AmphibianViewModel(
    private val amphibianRepository: AmphibianRepository
) : ViewModel() {

    // Observable state for the UI
    var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
        getAmphibians()
    }


    fun getAmphibians() {
        viewModelScope.launch {
            amphibianUiState = AmphibianUiState.Loading
            try {
                val amphibians = amphibianRepository.getAmphibians()
                amphibians.forEach { amphibian ->
                    println("Image URL: ${amphibian.imgSrc}")
                }

                if (amphibians.isEmpty()) {
                    amphibianUiState = AmphibianUiState.Error("No amphibians found.")
                } else {
                    amphibianUiState = AmphibianUiState.Success(amphibians)
                }
            } catch (e: IOException) {
                println("Network error: ${e.message}")
                amphibianUiState = AmphibianUiState.Error("Network error. Please try again.")
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                amphibianUiState = AmphibianUiState.Error("An unexpected error occurred.")
            }
        }
    }



    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AmphibianApplication
                AmphibianViewModel(application.container.amphibianRepository)
            }
        }
    }
}
