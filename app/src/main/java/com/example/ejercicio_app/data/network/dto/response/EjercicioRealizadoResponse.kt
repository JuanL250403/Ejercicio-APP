package com.example.ejercicio_app.data.network.dto.response

data class EjercicioRealizadoResponse(
    val id: String,
    val idEjercicio: String,
    val series: Int,
    val descanso: Int,
    val peso: Int,
    val tiempo: Int,
    val repeticiones: Int
)
