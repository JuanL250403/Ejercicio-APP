package com.example.ejercicio_app.data.response

import kotlin.collections.List

data class RutinaActualResponse(
    val id: Long,
    val nombre: String,
    val parteAnatomica: String,
    val detalle: List<DetalleRuitnaActualResponse>
)
