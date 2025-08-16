package com.example.formvalidation.network

object RegisterApiInstance {

    val api: RegisterApi by lazy {
        RegisterRetrofitInstance.retrofit.create(RegisterApi::class.java)
    }
}
