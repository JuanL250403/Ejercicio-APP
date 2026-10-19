package com.example.ejercicio_app.data.network.dto.request

data class DiaSemanaRequest(
    val id: String,
    val diaSemana: Int,
    val idRutina: String,
    val estado: Boolean
)
