package com.example.formvalidation.model

data class LoginResponse(
    val message: String,
    val userId: Int,
    val email: String
)
