package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.DetalleRutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioRealizadoEntity
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.relations.RutinaRealizadaDetalle

@Dao
interface RutinaRealizadaDao {

    @Insert
    suspend fun guardarRutinaActual(rutina: RutinaRealizadaEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarRutinasRealizadas(rutinas: List<RutinaRealizadaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarEjerciciosActuales(ejercicios: List<EjercicioRealizadoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDetalles(detalles: List<DetalleRutinaRealizadaEntity>)

    @Query("SELECT * FROM rutinas_realizadas")
    suspend fun obtenerRutinasDetalle(): List<RutinaRealizadaDetalle>

    @Transaction
    suspend fun guardarRutinas(
        rutinas: List<RutinaRealizadaEntity>,
        ejericicos: List<EjercicioRealizadoEntity>,
        detalles: List<DetalleRutinaRealizadaEntity>
    ) {
        guardarRutinasRealizadas(rutinas)
        guardarEjerciciosActuales(ejericicos)
        guardarDetalles(detalles)
    }

    @Transaction
    suspend fun guardarRutina(
        rutina: RutinaRealizadaEntity,
        detalles: List<DetalleRutinaRealizadaEntity>,
        ejercicios: List<EjercicioRealizadoEntity>
    ) {
        guardarRutinaActual(rutina)
        guardarEjerciciosActuales(ejercicios)
        guardarDetalles(detalles)
    }

    @Query("SELECT * FROM rutinas_realizadas WHERE id = :id")
    suspend fun obtenerRutina(id: String): RutinaRealizadaDetalle?
}