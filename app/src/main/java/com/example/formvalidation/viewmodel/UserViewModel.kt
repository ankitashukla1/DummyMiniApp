package com.example.formvalidation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.formvalidation.network.LoginApiInstance

import androidx.lifecycle.viewModelScope
import com.example.formvalidation.model.User
import com.example.formvalidation.network.RegisterApiInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    sealed class RegisterUiState {
        object Idle : RegisterUiState()
        object Loading : RegisterUiState()
        data class Success(val message: String) : RegisterUiState()
        data class Error(val error: String) : RegisterUiState()
    }

    var registeredEmail = ""
    var registeredPassword = ""

    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerState: StateFlow<RegisterUiState> = _registerState

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState = _loginState.asStateFlow()

//    fun registerUser(user: User, onResult: (Boolean) -> Unit)
//    {
//        viewModelScope.launch {
//            try {
//                val response = RegisterApiInstance.api.registerUser(user)
//                if (response.isSuccessful) {
//                    registeredEmail = user.email
//                    registeredPassword = user.password
//                    _registerState.value = "Success"
//                } else {
//                    _registerState.value = "Failed: ${response.message()}"
//                }
//            } catch (e: Exception) {
//                _registerState.value = "Error: ${e.localizedMessage}"
//            }
//        }
//    }

    fun registerUser(user: User) {
        viewModelScope.launch {
            _registerState.value = RegisterUiState.Loading
            try {
                val response = RegisterApiInstance.api.registerUser(user)
                _registerState.value = if (response.isSuccessful) {
                    RegisterUiState.Success(response.message())
                } else {
                    RegisterUiState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _registerState.value = RegisterUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                println("Fetching users from API...")
                val users = LoginApiInstance.api.getAllUsers()
                println("Fetched users: $users")

                val match = users.find { it.email == email && it.password == password }

                if (match != null) {
                    println("User matched: ${match.email}")
                } else {
                    println("No match found.")
                }

                onResult(match != null)
            } catch (e: Exception) {
                println("Error during login: ${e.message}")
                onResult(false)
            }
        }
    }



//    fun clearState() {
//        _registerState.value = null
//    }
}
