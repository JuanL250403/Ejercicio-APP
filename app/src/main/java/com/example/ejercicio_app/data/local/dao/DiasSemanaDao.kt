package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.ejercicio_app.data.local.entity.DiaSemanaRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.relations.DiaSemanaRutina
import com.example.ejercicio_app.data.local.entity.relations.DiaSemanaRutinaDetalle

@Dao
interface DiasSemanaDao {

    @Query("SELECT * FROM dias_semana_rutinas ORDER BY diaSemana")
    suspend fun obtenerDiasSemana(): List<DiaSemanaRutina>

    @Query("SELECT * FROM dias_semana_rutinas WHERE id = :id")
    suspend fun obtenerDiaSemanaDetalle(id: String): DiaSemanaRutinaDetalle?

    @Query("SELECT * FROM dias_semana_rutinas WHERE id = :id")
    suspend fun obtenerDiaSemana(id: String): DiaSemanaRutinaActualEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDiasSemana(dias: List<DiaSemanaRutinaActualEntity>)

    @Query("DELETE FROM dias_semana_rutinas WHERE id = :id")
    suspend fun eliminarDiaSemana(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDiaSemana(dia: DiaSemanaRutinaActualEntity)

    @Query("SELECT * FROM dias_semana_rutinas WHERE diaSemana = :dia")
    suspend fun obtenerRutinaDeDia(dia: Int): DiaSemanaRutinaDetalle?
}