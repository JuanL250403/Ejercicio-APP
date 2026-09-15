package com.example.ejercicio_app.network.services

import com.example.ejercicio_app.data.response.AuthResponse
import com.example.ejercicio_app.data.request.UsuarioInicioSesion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun iniciarSesion(@Body usuario: UsuarioInicioSesion): Response<AuthResponse>
}