package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.example.ejercicio_app.data.local.entity.relations.EjercicioActualDetalle

@Dao
interface EjercicioActualDao {
    @Query("SELECT * FROM ejercicios_actuales WHERE id = :id")
    suspend fun obtenerEjercicioActual(id: String): EjercicioActualDetalle?
}