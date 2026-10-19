package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.DiaSemanaRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.relations.DiaSemanaRutina
import com.example.ejercicio_app.data.local.entity.relations.DiaSemanaRutinaDetalle
import com.example.ejercicio_app.data.network.dto.request.DiaSemanaRequest
import com.example.ejercicio_app.data.network.dto.response.DiaSemanaResponse
import com.example.ejercicio_app.data.ui.DiaSemanaAgregarUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaDetalleUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaUiModel


fun List<DiaSemanaResponse>.toEntity(): List<DiaSemanaRutinaActualEntity> = this.map { d -> d.toEntity() }

fun DiaSemanaResponse.toEntity(): DiaSemanaRutinaActualEntity =
    DiaSemanaRutinaActualEntity(
        id = this.id,
        diaSemana = this.diaSemana,
        rutinaActualId = this.idRutina,
        estado = this.estado
    )

fun DiaSemanaRutinaDetalle.toUiModel(): DiaSemanaDetalleUiModel =
    DiaSemanaDetalleUiModel(
        id = this.diaSemana.id,
        diaSemana = this.diaSemana.diaSemana,
        rutina = this.rutina.toUiModel(),
        estado = this.diaSemana.estado
    )

fun DiaSemanaAgregarUiModel.toEntity(): DiaSemanaRutinaActualEntity =
    DiaSemanaRutinaActualEntity(
        diaSemana = this.diaSemana,
        estado = false,
        rutinaActualId = this.rutinaId
    )

fun List<DiaSemanaRutina>.toUiModel(): List<DiaSemanaUiModel> = this.map { d -> d.toUiModel() }

fun DiaSemanaRutina.toUiModel(): DiaSemanaUiModel =
    DiaSemanaUiModel(
        id = this.diaSemana.id,
        estado = this.diaSemana.estado,
        rutina = this.rutina.info.nombre,
        diaSemana = this.diaSemana.diaSemana
    )

fun List<DiaSemanaRutina>.toRequest(): List<DiaSemanaRequest> = this.map { d -> d.toRequest() }

fun DiaSemanaRutina.toRequest(): DiaSemanaRequest =
    DiaSemanaRequest(
        id = this.diaSemana.id,
        diaSemana = this.diaSemana.diaSemana,
        estado = this.diaSemana.estado,
        idRutina = this.rutina.id
    )