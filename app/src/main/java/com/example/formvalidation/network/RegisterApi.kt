package com.example.formvalidation.network

import com.example.formvalidation.model.Register
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterApi {

    @GET("register")
    suspend fun getUsers(): List<Register>

    @POST("register")
    suspend fun registerUser(@Body user: Register): Response<Register>
}
