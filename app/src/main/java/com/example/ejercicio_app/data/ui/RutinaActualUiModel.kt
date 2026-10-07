package com.example.ejercicio_app.data.ui

import com.example.ejercicio_app.data.network.dto.response.DetalleRuitnaActualResponse

data class RutinaActualUiModel(
    val id: String? = null,
    val nombre: String,
    val parteAnatomica: Long,
    val detalle: List<DetalleRutinaActualUiModel>
)
