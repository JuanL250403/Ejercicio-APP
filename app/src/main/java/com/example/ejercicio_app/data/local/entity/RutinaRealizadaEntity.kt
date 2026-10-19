package com.example.ejercicio_app.data.local.entity

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Date
import java.util.UUID

@Entity(tableName = "rutinas_realizadas")
data class RutinaRealizadaEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @Embedded val info: RutinaInfo,
    val tiempoRealizacion: Int,
    val diaSemena: Int,
    val fechaRealizacion: String = Date().toString()
)
