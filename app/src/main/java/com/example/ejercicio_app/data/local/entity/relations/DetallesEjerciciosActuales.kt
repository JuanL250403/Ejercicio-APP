package com.example.ejercicio_app.data.local.entity.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity

data class DetallesEjerciciosActuales(
    @Embedded val detalle: DetalleRutinaActualEntity,
    @Relation(
        entity = EjercicioActualEntity::class,
        parentColumns = ["ejercicioActualId"],
        entityColumns = ["id"]
    )
    val ejercicio: EjercicioActualDetalle
)
