package com.example.ejercicio_app.data.ui

import com.example.ejercicio_app.data.network.dto.response.DetalleRuitnaActualResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class RutinaRealizadaUiModel(
    val id: String? = null,
    val nombre: String,
    val diaSemana: Int,
    val fechaRealizacion: LocalDate,
    val tiempoRealizacion: Int,
    val parteAnatomica: Long,
    val detalle: List<DetalleRutinaRealizadaUiModel>
)
