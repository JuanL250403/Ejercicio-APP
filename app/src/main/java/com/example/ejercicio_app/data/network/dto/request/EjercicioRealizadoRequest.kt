package com.example.ejercicio_app.data.network.dto.request

data class EjercicioRealizadoRequest(
    val id: String,
    val idEjercicio: String,
    val series: Int,
    val descanso: Int,
    val peso: Int,
    val tiempo: Int,
    val repeticiones: Int
)
