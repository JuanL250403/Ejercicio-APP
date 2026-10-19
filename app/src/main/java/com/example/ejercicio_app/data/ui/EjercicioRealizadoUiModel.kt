package com.example.ejercicio_app.data.ui

data class EjercicioRealizadoUiModel(
    val nombre: String? = null,
    val id: String? = null,
    val ejercicio: EjercicioDataSetUiModel,
    val series: Int,
    val descanso: Int,
    val peso: Int,
    val tiempo: Int,
    val repeticiones: Int
)
