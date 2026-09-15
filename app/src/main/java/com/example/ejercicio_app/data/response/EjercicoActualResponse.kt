package com.example.ejercicio_app.data.response

data class EjercicoActualResponse(
    val id: Long,
    val idEjercicio: Int,
    val series: Int,
    val descanso: Int,
    val peso: Int,
    val tiempo: Int,
    val repeticiones: Int
)
