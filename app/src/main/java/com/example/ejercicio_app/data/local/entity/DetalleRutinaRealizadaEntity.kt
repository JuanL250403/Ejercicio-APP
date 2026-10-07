package com.example.ejercicio_app.data.local.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "detalles_rutinas_relizadas",
    foreignKeys = [
        ForeignKey(
            entity = RutinaRealizadaEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutinaRealizadaId"]
        ),
        ForeignKey(
            entity = EjercicioRealizadoEntity::class,
            parentColumns = ["id"],
            childColumns = ["ejercicioRealizadoId"]
        )
    ]
)
data class DetalleRutinaRealizadaEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),

    val rutinaRealizadaId: UUID,
    val ejercicioRealizadoId: UUID,
)
