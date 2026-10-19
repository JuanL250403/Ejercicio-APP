package com.example.ejercicio_app.data.local.entity.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity

data class RutinaActualDetalle(
    @Embedded val rutina: RutinaActualEntity,
    @Relation(
        entity = DetalleRutinaActualEntity::class,
        parentColumns = ["id"],
        entityColumns = ["rutinaRealizadaId"]
    )
    val detalles: List<DetallesEjerciciosActuales>
)
