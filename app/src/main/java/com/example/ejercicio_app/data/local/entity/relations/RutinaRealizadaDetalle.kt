package com.example.ejercicio_app.data.local.entity.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.DetalleRutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaRealizadaEntity

data class RutinaRealizadaDetalle(
    @Embedded val rutina: RutinaRealizadaEntity,
    @Relation(
        entity = DetalleRutinaRealizadaEntity::class,
        parentColumns = ["id"],
        entityColumns = ["rutinaRealizadaId"]
    )
    val detalles: List<DetallesEjerciciosRealizados>
)
