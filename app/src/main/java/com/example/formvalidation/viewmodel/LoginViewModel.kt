package com.example.formvalidation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formvalidation.model.LoginRequest
import com.example.formvalidation.model.LoginResponse
import com.example.formvalidation.model.Register
import com.example.formvalidation.network.LoginApiInstance
import com.example.formvalidation.network.RegisterApiInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val registeredUsers = RegisterApiInstance.api.getUsers()
                val matchedUser = registeredUsers.find {
                    it.email == request.email && it.password == request.password
                }

                if (matchedUser != null) {
                    val response = LoginApiInstance.api.loginUser(request)
                    _loginState.value = if (response.isSuccessful) "success"
                    else "Failed to store login attempt"
                } else {
                    _loginState.value = "Email and password not matching"
                }
            } catch (e: Exception) {
                _loginState.value = "Error: ${e.message}"
            }
        }
    }

}
