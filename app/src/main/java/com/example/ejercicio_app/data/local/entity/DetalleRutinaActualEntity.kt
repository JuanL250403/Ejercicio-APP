package com.example.ejercicio_app.data.local.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "detalles_rutinas_actuales",
    foreignKeys = [
        ForeignKey(
            entity = RutinaActualEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutinaRealizadaId"]
        ),
        ForeignKey(
            entity = EjercicioActualEntity::class,
            parentColumns = ["id"],
            childColumns = ["ejercicioActualId"]
        )
    ]
)
data class DetalleRutinaActualEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),

    val rutinaRealizadaId: String,
    val ejercicioActualId: String,
)
