package com.example.ejercicio_app.data.network.services

import com.example.ejercicio_app.data.network.dto.request.DiaSemanaRequest
import com.example.ejercicio_app.data.network.dto.response.DiaSemanaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaSemanaService {
    @GET("api/semana/{id}")
    suspend fun obtenerDiasSemana(@Path("id") usuarioId: Long): Response<List<DiaSemanaResponse>>

    @POST("api/semana/{id}")
    suspend fun sincronizarRutinas(@Path("id") usuarioId: Long, @Body dias: List<DiaSemanaRequest>): Response<List<DiaSemanaResponse>>
}