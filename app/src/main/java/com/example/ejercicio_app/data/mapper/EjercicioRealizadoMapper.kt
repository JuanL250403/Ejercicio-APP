package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.EjercicioInfo
import com.example.ejercicio_app.data.local.entity.EjercicioRealizadoEntity
import com.example.ejercicio_app.data.local.entity.relations.EjercicioRealizadoDetalle
import com.example.ejercicio_app.data.network.dto.request.EjercicioRealizadoRequest
import com.example.ejercicio_app.data.network.dto.response.EjercicioRealizadoResponse
import com.example.ejercicio_app.data.ui.EjercicioRealizadoUiModel

fun List<EjercicioRealizadoResponse>.toEntity(): List<EjercicioRealizadoEntity> = this.map { e -> e.toEntity()}

fun EjercicioRealizadoResponse.toEntity(): EjercicioRealizadoEntity =
    EjercicioRealizadoEntity(
        id = this.id,
        info = EjercicioInfo(
            this.idEjercicio,
            this.series,
            this.descanso,
            this.peso,
            this.tiempo,
            this.repeticiones
        )
    )

fun EjercicioRealizadoDetalle.toUiModel(): EjercicioRealizadoUiModel =
    EjercicioRealizadoUiModel(
        id = this.ejercicio.id,
        ejercicio =  this.detalle.toUiModel(),
        series =  this.ejercicio.info.series,
        descanso =  this.ejercicio.info.descanso,
        peso =  this.ejercicio.info.peso,
        tiempo =  this.ejercicio.info.tiempo,
        repeticiones =  this.ejercicio.info.repeticiones
    )

fun EjercicioRealizadoUiModel.toEntity(): EjercicioRealizadoEntity =
    EjercicioRealizadoEntity(
        info = EjercicioInfo(
            this.ejercicio.id,
            this.series,
            this.descanso,
            this.peso,
            this.tiempo,
            this.repeticiones
        )
    )

fun List<EjercicioRealizadoDetalle>.toRequest(): List<EjercicioRealizadoRequest> = this.map { e -> e.toRequest() }

fun EjercicioRealizadoDetalle.toRequest(): EjercicioRealizadoRequest =
    EjercicioRealizadoRequest(
        id = this.ejercicio.id,
        idEjercicio = this.ejercicio.info.idEjercicio,
        series = this.ejercicio.info.series,
        repeticiones = this.ejercicio.info.repeticiones,
        descanso = this.ejercicio.info.descanso,
        tiempo = this.ejercicio.info.tiempo,
        peso = this.ejercicio.info.peso,
    )