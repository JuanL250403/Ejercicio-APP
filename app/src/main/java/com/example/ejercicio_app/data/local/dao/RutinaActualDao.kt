package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity
import com.example.ejercicio_app.data.local.entity.relations.RutinaActualDetalle

@Dao
interface RutinaActualDao {

    @Insert
    suspend fun guardarRutinaActual(rutina: RutinaActualEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarRutinasActuales(rutinas: List<RutinaActualEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarEjerciciosActuales(ejercicios: List<EjercicioActualEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDetalles(detalles: List<DetalleRutinaActualEntity>)

    @Query("DELETE FROM rutinas_actuales WHERE id = :id")
    suspend fun eliminarRutina(id: String)

    @Query("DELETE FROM ejercicios_actuales WHERE id IN (:ids)")
    suspend fun eliminarEjercicio(ids: List<String>)

    @Query("DELETE FROM detalles_rutinas_actuales WHERE id IN (:ids)")
    suspend fun eliminarDetalles(ids: List<String>)

    @Query("DELETE FROM dias_semana_rutinas WHERE rutinaActualId = :id")
    suspend fun eliminarDias(id: String)

    @Transaction
    suspend fun guardarRutinas(
        rutinas: List<RutinaActualEntity>,
        ejericicos: List<EjercicioActualEntity>,
        detalles: List<DetalleRutinaActualEntity>
    ) {
        guardarRutinasActuales(rutinas)
        guardarEjerciciosActuales(ejericicos)
        guardarDetalles(detalles)
    }

    @Transaction
    suspend fun guardarRutina(
        rutina: RutinaActualEntity,
        detalles: List<DetalleRutinaActualEntity>,
        ejercicios: List<EjercicioActualEntity>
    ) {
        guardarRutinaActual(rutina)
        guardarEjerciciosActuales(ejercicios)
        guardarDetalles(detalles)
    }

    @Transaction
    suspend fun eliminarRutina(
        rutinaId: String,
        detallesIds: List<String>,
        ejerciciosIds: List<String>,
    ) {
        eliminarDias(rutinaId)
        eliminarDetalles(detallesIds)
        eliminarRutina(rutinaId)
        eliminarEjercicio(ejerciciosIds)
    }


    @Transaction
    @Query("SELECT * FROM rutinas_actuales WHERE nombre LIKE '%' || :nombre || '%'")
    suspend fun obtenerRutinasDetalleNombre(nombre: String): List<RutinaActualDetalle>

    @Transaction
    @Query("SELECT * FROM rutinas_actuales")
    suspend fun obtenerRutinasDetalle(): List<RutinaActualDetalle>

    @Transaction
    @Query("SELECT * FROM rutinas_actuales WHERE id = :id")
    suspend fun obtenerRutinaDetalle(id: String): RutinaActualDetalle?
}