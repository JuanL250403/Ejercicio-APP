package com.example.ejercicio_app.data.mapper

import com.example.ejercicio_app.data.local.entity.EjercicioDataSet
import com.example.ejercicio_app.data.ui.EjercicioDataSetUiModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()
val pasosInstruccionesTipo = object : TypeToken<Map<String, List<String>>>() {}.type

fun List<EjercicioDataSet>.toUiModel(): List<EjercicioDataSetUiModel> = this.map { e -> e.toUiModel() }

fun EjercicioDataSet.toUiModel(): EjercicioDataSetUiModel =
    EjercicioDataSetUiModel(
        id = this.id,
        name = this.name,
        category = this.category,
        bodyPart = this.bodyPart,
        equipment = this.equipment,
        muscleGroup = this.muscleGroup,
        secondaryMuscles = this.secondaryMuscles.split(','),
        target = this.target,
        instructionSteps = gson.fromJson(this.instructionSteps, pasosInstruccionesTipo) ?: mapOf(),
        image = this.image,
        gifUrl = this.gifUrl,
    )