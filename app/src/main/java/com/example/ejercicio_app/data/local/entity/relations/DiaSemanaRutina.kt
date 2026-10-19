package com.example.ejercicio_app.data.local.entity.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.ejercicio_app.data.local.entity.DiaSemanaRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity

data class DiaSemanaRutina(
    @Embedded val diaSemana: DiaSemanaRutinaActualEntity,
    @Relation(
        entity = RutinaActualEntity::class,
        parentColumns = ["rutinaActualId"],
        entityColumns = ["id"]
    )
    val rutina: RutinaActualEntity
)
