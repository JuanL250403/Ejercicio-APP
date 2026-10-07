package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.example.ejercicio_app.data.local.entity.EjercicioDataSet

@Dao
interface EjercicioDataSetDao {

    @Query("SELECT * FROM ejercicios")
    suspend fun obtenerEjerciciosDataset(): List<EjercicioDataSet>

    @Query("SELECT * FROM ejercicios WHERE id = :id")
    suspend fun obtenerEjercicioDataset(id: String): EjercicioDataSet
}