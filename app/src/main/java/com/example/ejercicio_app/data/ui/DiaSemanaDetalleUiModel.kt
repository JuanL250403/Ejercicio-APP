package com.example.ejercicio_app.data.ui

data class DiaSemanaDetalleUiModel(
    val id: String,
    val diaSemana: Int,
    val rutina: RutinaActualUiModel,
    val estado: Boolean
)
