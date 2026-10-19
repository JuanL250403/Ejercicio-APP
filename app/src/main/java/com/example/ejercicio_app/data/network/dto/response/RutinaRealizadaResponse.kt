package com.example.ejercicio_app.data.network.dto.response

import java.time.LocalDate
import java.time.LocalDateTime

data class RutinaRealizadaResponse(
    val id: String,
    val nombre: String,
    val parteAnatomica: Long,
    val diaSemana: Int,
    val tiempoRealizacion: Int,
    val fechaRealizacion: LocalDateTime,
    val detalle: List<DetalleRutinaRealizadaResponse>
)
