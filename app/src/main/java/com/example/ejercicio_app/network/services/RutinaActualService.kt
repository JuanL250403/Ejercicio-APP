package com.example.ejercicio_app.network.services

import com.example.ejercicio_app.data.response.RutinaActualResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RutinaActualService {

    @GET("api/rutinas/actuales/{id}")
    suspend fun obtenerRutinasActuales(@Path("id") usuarioId: Int): Response<List<RutinaActualResponse>>
}