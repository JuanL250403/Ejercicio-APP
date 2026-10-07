package com.example.ejercicio_app.data.local.entity

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "ejercicios_actuales",
    foreignKeys = [
        ForeignKey(
            entity = EjercicioDataSet::class,
            parentColumns = ["id"],
            childColumns = ["idEjercicio"]
        )
    ]
)
data class EjercicioActualEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @Embedded val info: EjercicioInfo,
)
