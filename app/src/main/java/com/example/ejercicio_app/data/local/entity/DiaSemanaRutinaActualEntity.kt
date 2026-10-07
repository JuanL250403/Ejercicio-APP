package com.example.ejercicio_app.data.local.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import java.time.DayOfWeek
import java.util.UUID

@Entity(
    tableName = "dias_semana_rutinas",
    foreignKeys = [
        ForeignKey(
            entity = RutinaActualEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutinaActualId"]
        )
    ]
)
data class DiaSemanaRutinaActualEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val diaSemana: Int,
    val rutinaActualId: UUID,
    val estado: Boolean
)
