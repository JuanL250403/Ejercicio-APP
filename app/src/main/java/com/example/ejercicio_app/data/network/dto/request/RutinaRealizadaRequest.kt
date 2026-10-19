package com.example.ejercicio_app.data.network.dto.request

import java.time.LocalDate

data class RutinaRealizadaRequest(
    val id: String,
    val nombre: String,
    val parteAnatomica: Long,
    val tiempoRealizacion: Int,
    val fechaRealizacion: String,
    val diaSemana: Int,
    val detalle: List<DetalleRutinaRealizadaRequest>
)
