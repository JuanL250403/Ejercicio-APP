package com.example.ejercicio_app.data.ui

import com.google.gson.annotations.SerializedName

data class EjercicioDataSetUiModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    val category: String,

    val bodyPart: String,

    val equipment: String,

    val muscleGroup: String,

    val secondaryMuscles: List<String>,

    val target: String,

    val instructionSteps: Map<String, List<String>>,

    val image: String,

    val gifUrl: String,
)