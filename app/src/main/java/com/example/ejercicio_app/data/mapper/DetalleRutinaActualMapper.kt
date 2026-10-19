package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.relations.DetallesEjerciciosActuales
import com.example.ejercicio_app.data.network.dto.request.DetalleRutinaActualRequest
import com.example.ejercicio_app.data.network.dto.response.DetalleRuitnaActualResponse
import com.example.ejercicio_app.data.ui.DetalleRutinaActualUiModel
import com.example.ejercicio_app.data.ui.DetalleRutinaRealizadaUiModel

fun DetalleRuitnaActualResponse.toEntity(rutinaId: String): DetalleRutinaActualEntity =
    DetalleRutinaActualEntity(
        id = this.id,
        rutinaRealizadaId = rutinaId,
        ejercicioActualId = this.ejercicio.id
    )


fun List<DetalleRuitnaActualResponse>.toEntity(rutinaId: String): List<DetalleRutinaActualEntity> =
    this.map { d -> d.toEntity(rutinaId) }


fun List<DetallesEjerciciosActuales>.toUiModel(): List<DetalleRutinaActualUiModel> = this.map { d -> d.toUiModel() }

fun DetallesEjerciciosActuales.toUiModel(): DetalleRutinaActualUiModel =
    DetalleRutinaActualUiModel(
        this.detalle.id,
        this.ejercicio.toUiModel()
    )

fun List<DetallesEjerciciosActuales>.toRequest(): List<DetalleRutinaActualRequest> = this.map { d -> d.toRequest() }

fun DetallesEjerciciosActuales.toRequest(): DetalleRutinaActualRequest =
    DetalleRutinaActualRequest(
        id = this.detalle.id,
        ejercicio = this.ejercicio.toRequest()
    )

fun List<DetalleRutinaActualUiModel>.toRealizadaUiModel(): List<DetalleRutinaRealizadaUiModel> = this.map { d -> d.toRealizadaUiModel() }

fun DetalleRutinaActualUiModel.toRealizadaUiModel(): DetalleRutinaRealizadaUiModel =
    DetalleRutinaRealizadaUiModel(
        ejercicio = this.ejercicio.toRealizadoUiModel()
    )




