package com.example.ejercicio_app.data.network.dto.request

data class RutinaActualRequest(
    val id: String,
    val nombre: String,
    val parteAnatomica: Long,
    val detalle: List<DetalleRutinaActualRequest>
)
