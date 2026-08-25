package com.example.ejercicio_app.network

import com.example.ejercicio_app.BuildConfig
import com.example.ejercicio_app.network.services.AuthApiService
import com.example.ejercicio_app.network.services.UsuarioService
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

    val usuarioService: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }
}