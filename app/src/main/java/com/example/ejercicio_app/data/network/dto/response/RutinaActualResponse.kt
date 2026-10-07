package com.example.ejercicio_app.data.network.dto.response

import kotlin.collections.List

data class RutinaActualResponse(
    val id: String,
    val nombre: String,
    val parteAnatomica: Long,
    val detalle: List<DetalleRuitnaActualResponse>
)
