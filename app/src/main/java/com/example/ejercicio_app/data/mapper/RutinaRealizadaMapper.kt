package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.RutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaInfo
import com.example.ejercicio_app.data.local.entity.RutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.relations.RutinaRealizadaDetalle
import com.example.ejercicio_app.data.network.dto.request.RutinaRealizadaRequest
import com.example.ejercicio_app.data.network.dto.response.RutinaActualResponse
import com.example.ejercicio_app.data.network.dto.response.RutinaRealizadaResponse
import com.example.ejercicio_app.data.ui.RutinaRealizadaUiModel
import java.time.LocalDate

fun List<RutinaRealizadaResponse>.toEntity(): List<RutinaRealizadaEntity> =
    this.map { r -> r.toEntity() }

fun RutinaRealizadaResponse.toEntity(): RutinaRealizadaEntity =
    RutinaRealizadaEntity(
        id = this.id,
        tiempoRealizacion = this.tiempoRealizacion,
        diaSemena = this.diaSemana,
        fechaRealizacion = this.fechaRealizacion.toString(),
        info = RutinaInfo(
            nombre = this.nombre,
            parteAnatomica = this.parteAnatomica
        )
    )


fun List<RutinaRealizadaDetalle>.toUiModel(): List<RutinaRealizadaUiModel> =
    this.map { r -> r.toUiModel() }

fun RutinaRealizadaDetalle.toUiModel(): RutinaRealizadaUiModel =
    RutinaRealizadaUiModel(
        id = this.rutina.id,
        nombre = this.rutina.info.nombre,
        diaSemana = this.rutina.diaSemena,
        fechaRealizacion = LocalDate.parse(this.rutina.fechaRealizacion),
        tiempoRealizacion = this.rutina.tiempoRealizacion,
        parteAnatomica = this.rutina.info.parteAnatomica,
        detalle = this.detalles.toUiModel()
    )

fun RutinaRealizadaUiModel.toEntity(): RutinaRealizadaEntity =
    RutinaRealizadaEntity(
        tiempoRealizacion = this.tiempoRealizacion,
        diaSemena = this.diaSemana,
        fechaRealizacion = this.fechaRealizacion.toString(),
        info = RutinaInfo(
            nombre = this.nombre,
            parteAnatomica = this.parteAnatomica
        )
    )

fun List<RutinaRealizadaDetalle>.toRequest(): List<RutinaRealizadaRequest> = this.map { r -> r.toRequest() }

fun RutinaRealizadaDetalle.toRequest(): RutinaRealizadaRequest =
    RutinaRealizadaRequest(
        id = this.rutina.id,
        nombre = this.rutina.info.nombre,
        parteAnatomica = this.rutina.info.parteAnatomica,
        tiempoRealizacion = this.rutina.tiempoRealizacion,
        fechaRealizacion = this.rutina.fechaRealizacion,
        diaSemana = this.rutina.diaSemena,
        detalle = this.detalles.toRequest()
    )