package com.example.formvalidation.network

import com.example.formvalidation.model.LoginRequest
import com.example.formvalidation.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @GET("login")
    suspend fun getLoginAttempts(): List<LoginResponse>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginRequest>
}
