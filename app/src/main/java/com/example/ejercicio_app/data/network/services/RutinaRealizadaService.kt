package com.example.ejercicio_app.data.network.services

import com.example.ejercicio_app.data.network.dto.request.RutinaRealizadaRequest
import com.example.ejercicio_app.data.network.dto.response.RutinaRealizadaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RutinaRealizadaService {
    @GET("api/rutinas/realizadas/{id}")
    suspend fun obtenerRutinasRealizadas(@Path("id") usuarioId: Long): Response<List<RutinaRealizadaResponse>>

    @POST("api/rutinas/realizadas/{id}")
    suspend fun sincronizarRutinas(@Path("id") usuarioId: Long, @Body rutinas: List<RutinaRealizadaRequest>)
}