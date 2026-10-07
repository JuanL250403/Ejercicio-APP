package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioInfo
import com.example.ejercicio_app.data.local.entity.relations.EjercicioActualDetalle
import com.example.ejercicio_app.data.network.dto.request.EjercicioActualRequest
import com.example.ejercicio_app.data.network.dto.response.EjercicoActualResponse
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel

fun EjercicoActualResponse.toEntity(): EjercicioActualEntity = EjercicioActualEntity(
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


fun List<EjercicoActualResponse>.toEntity(): List<EjercicioActualEntity> =
    this.map { e -> e.toEntity() }


fun EjercicioActualDetalle.toUiModel(): EjercicioActualUiModel =
    EjercicioActualUiModel(
        id = this.ejercicio.id,
        ejercicio =  this.detalle.toUiModel(),
        series =  this.ejercicio.info.series,
        descanso =  this.ejercicio.info.descanso,
        peso =  this.ejercicio.info.peso,
        tiempo =  this.ejercicio.info.tiempo,
        repeticiones =  this.ejercicio.info.repeticiones
    )

fun EjercicioActualUiModel.toEntity(): EjercicioActualEntity =
    EjercicioActualEntity(
        info = EjercicioInfo(
            idEjercicio = this.ejercicio.id,
            series = this.series,
            repeticiones = this.repeticiones,
            descanso = this.descanso,
            tiempo = this.tiempo,
            peso = this.peso,
        )
    )

fun EjercicioActualDetalle.toRequest(): EjercicioActualRequest =
    EjercicioActualRequest(
        id = this.ejercicio.id,
        idEjercicio = this.ejercicio.info.idEjercicio,
        series = this.ejercicio.info.series,
        repeticiones = this.ejercicio.info.repeticiones,
        descanso = this.ejercicio.info.descanso,
        tiempo = this.ejercicio.info.tiempo,
        peso = this.ejercicio.info.peso,
    )