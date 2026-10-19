package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.DetalleRutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.relations.DetallesEjerciciosRealizados
import com.example.ejercicio_app.data.network.dto.request.DetalleRutinaRealizadaRequest
import com.example.ejercicio_app.data.network.dto.response.DetalleRutinaRealizadaResponse
import com.example.ejercicio_app.data.ui.DetalleRutinaActualUiModel
import com.example.ejercicio_app.data.ui.DetalleRutinaRealizadaUiModel

fun List<DetalleRutinaRealizadaResponse>.toEntity(rutinaId: String): List<DetalleRutinaRealizadaEntity> = this.map { d -> d.toEntity(rutinaId) }

fun DetalleRutinaRealizadaResponse.toEntity(rutinaId: String): DetalleRutinaRealizadaEntity =
    DetalleRutinaRealizadaEntity(
        id = this.id,
        rutinaId,
        ejercicioRealizadoId = this.ejercicio.id
    )

fun List<DetallesEjerciciosRealizados>.toUiModel(): List<DetalleRutinaRealizadaUiModel> = this.map { d -> d.toUiModel() }

fun DetallesEjerciciosRealizados.toUiModel(): DetalleRutinaRealizadaUiModel =
    DetalleRutinaRealizadaUiModel(
        id = this.detalle.id,
        ejercicio = this.ejercicio.toUiModel()
    )

fun List<DetallesEjerciciosRealizados>.toRequest(): List<DetalleRutinaRealizadaRequest> = this.map { d -> d.toRequest() }

fun DetallesEjerciciosRealizados.toRequest(): DetalleRutinaRealizadaRequest =
    DetalleRutinaRealizadaRequest(
        id = this.detalle.id,
        ejercicio = this.ejercicio.toRequest()
    )
