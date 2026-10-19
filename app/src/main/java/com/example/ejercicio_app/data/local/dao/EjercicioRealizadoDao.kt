package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.example.ejercicio_app.data.local.entity.relations.EjercicioRealizadoDetalle

@Dao
interface EjercicioRealizadoDao {
    @Query("SELECT * FROM ejercicios_realizados WHERE id = :id")
    suspend fun obtenerEjercicioRealizado(id: String): EjercicioRealizadoDetalle?
}