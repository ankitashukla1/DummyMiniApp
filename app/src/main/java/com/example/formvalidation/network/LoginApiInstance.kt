package com.example.formvalidation.network

object LoginApiInstance {
    val api: LoginApi by lazy {
        LoginRetrofitInstance.retrofit.create(LoginApi::class.java)
    }
}
