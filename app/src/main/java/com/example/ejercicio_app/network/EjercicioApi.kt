package com.example.ejercicio_app.network

import com.example.ejercicio_app.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EjercicioApi {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val authService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}