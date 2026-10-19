package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioInfo
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaInfo
import com.example.ejercicio_app.data.local.entity.relations.RutinaActualDetalle
import com.example.ejercicio_app.data.network.dto.request.RutinaActualRequest
import com.example.ejercicio_app.data.network.dto.response.DetalleRuitnaActualResponse
import com.example.ejercicio_app.data.network.dto.response.EjercicoActualResponse
import com.example.ejercicio_app.data.network.dto.response.RutinaActualResponse
import com.example.ejercicio_app.data.ui.RutinaActualUiModel


fun List<RutinaActualResponse>.toEntity(): List<RutinaActualEntity> = this.map { r -> r.toEntity() }


fun RutinaActualResponse.toEntity(): RutinaActualEntity = RutinaActualEntity(
    id = this.id,
    info = RutinaInfo(
        nombre = this.nombre,
        parteAnatomica = this.parteAnatomica
    )
)


fun List<RutinaActualDetalle>.toUiModel(): List<RutinaActualUiModel> = this.map { r -> r.toUiModel() }

fun RutinaActualDetalle.toUiModel(): RutinaActualUiModel =
    RutinaActualUiModel(
        id = this.rutina.id,
        nombre = this.rutina.info.nombre,
        parteAnatomica = this.rutina.info.parteAnatomica,
        detalle = this.detalles.toUiModel()
    )

fun RutinaActualUiModel.toEntity(): RutinaActualEntity =
    RutinaActualEntity(
        info = RutinaInfo(
            nombre = this.nombre,
            parteAnatomica = this.parteAnatomica
        )
    )

fun List<RutinaActualDetalle>.toRequest(): List<RutinaActualRequest> = this.map { r -> r.toRequest() }

fun RutinaActualDetalle.toRequest(): RutinaActualRequest =
    RutinaActualRequest(
        id = this.rutina.id,
        nombre = this.rutina.info.nombre,
        parteAnatomica = this.rutina.info.parteAnatomica,
        detalle = this.detalles.toRequest()
    )

fun RutinaActualEntity.toUiModel(): RutinaActualUiModel =
    RutinaActualUiModel(
        id = this.id,
        nombre = this.info.nombre,
        parteAnatomica = this.info.parteAnatomica,
        listOf()
    )
