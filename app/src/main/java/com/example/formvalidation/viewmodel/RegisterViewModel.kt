package com.example.formvalidation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formvalidation.model.Register
import com.example.formvalidation.network.RegisterApiInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel : ViewModel() {

    sealed class RegisterUiState {
        object Idle : RegisterUiState()
        object Loading : RegisterUiState()
        data class Success(val message: String) : RegisterUiState()
        data class Error(val error: String) : RegisterUiState()
    }

    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerState: StateFlow<RegisterUiState> = _registerState

    fun registerUser(user: Register) {
        viewModelScope.launch {
            _registerState.value = RegisterUiState.Loading
            try {
                val response = RegisterApiInstance.api.registerUser(user)
                if (response.isSuccessful) {
                    _registerState.value = RegisterUiState.Success("Registration successful")
                } else {
                    _registerState.value = RegisterUiState.Error("Registration failed: ${response.message()}")
                }
            } catch (e: IOException) {
                _registerState.value = RegisterUiState.Error("Network Error: ${e.message}")
            } catch (e: HttpException) {
                _registerState.value = RegisterUiState.Error("HTTP Error: ${e.message}")
            } catch (e: Exception) {
                _registerState.value = RegisterUiState.Error("Error: ${e.message}")
            }
        }
    }
}
