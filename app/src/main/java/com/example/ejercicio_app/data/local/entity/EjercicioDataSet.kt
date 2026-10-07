package com.example.ejercicio_app.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "ejercicios")
data class EjercicioDataSet(
    @PrimaryKey val id: String,

    val name: String,

    val category: String,

    val bodyPart: String,

    val equipment: String,

    val muscleGroup: String,

    val secondaryMuscles: String,

    val target: String,

    val instructionSteps: String,

    val image: String,

    val gifUrl: String,
)
