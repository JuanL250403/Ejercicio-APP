package com.example.ejercicio_app.data.local.entity.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.ejercicio_app.data.local.entity.EjercicioDataSet
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity

data class EjercicioActualDetalle(
    @Embedded val ejercicio: EjercicioActualEntity,
    @Relation(
        entity = EjercicioDataSet::class,
        parentColumns = ["idEjercicio"],
        entityColumns = ["id"]
    )
    val detalle: EjercicioDataSet
)
