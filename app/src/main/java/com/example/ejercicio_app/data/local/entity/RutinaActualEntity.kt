package com.example.ejercicio_app.data.local.entity

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.util.UUID

@Entity(tableName = "rutinas_actuales")
data class RutinaActualEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @Embedded val info: RutinaInfo,
)
