package com.example.ejercicio_app.data.local.entity.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.DetalleRutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioRealizadoEntity

data class DetallesEjerciciosRealizados(
    @Embedded val detalle: DetalleRutinaRealizadaEntity,
    @Relation(
        entity = EjercicioRealizadoEntity::class,
        parentColumns = ["ejercicioRealizadoId"],
        entityColumns = ["id"]
    )
    val ejercicio: EjercicioRealizadoDetalle
)
