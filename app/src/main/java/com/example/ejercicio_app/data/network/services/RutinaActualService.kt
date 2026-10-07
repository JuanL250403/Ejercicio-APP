package com.example.ejercicio_app.data.network.services

import com.example.ejercicio_app.data.network.dto.request.RutinaActualRequest
import com.example.ejercicio_app.data.network.dto.response.RutinaActualResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RutinaActualService {

    @GET("api/rutinas/actuales/{id}")
    suspend fun obtenerRutinasActuales(@Path("id") usuarioId: Long): Response<List<RutinaActualResponse>>

    @POST("api/rutinas/actuales/{id}")
    suspend fun sincronizarRutinas(@Path("id")  usuarioId: Long, @Body rutinas: List<RutinaActualRequest>): Response<List<RutinaActualResponse>>
}