package com.example.ejercicio_app.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.RawQuery
import androidx.room3.Transaction
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface DatabaseDao {
    @Query("DELETE FROM dias_semana_rutinas")
    suspend fun eliminarDiasSemana()

    @Query("DELETE FROM detalles_rutinas_actuales")
    suspend fun eliminarDetallesRutinaActual()

    @Query("DELETE FROM detalles_rutinas_relizadas")
    suspend fun eliminarDetallesRutinaRealizada()

    @Query("DELETE FROM rutinas_realizadas")
    suspend fun eliminarRutinaRealizada()

    @Query("DELETE FROM rutinas_actuales")
    suspend fun eliminarRutinaActual()

    @Query("DELETE FROM ejercicios_actuales")
    suspend fun eliminarEjercicioActual()

    @Query("DELETE FROM ejercicios_realizados")
    suspend fun eliminarEjercicioRealizado()

    @Transaction
    suspend fun eliminarDatosUsuario() {
        eliminarDiasSemana()
        eliminarDetallesRutinaActual()
        eliminarDetallesRutinaRealizada()
        eliminarRutinaRealizada()
        eliminarRutinaActual()
        eliminarEjercicioActual()
        eliminarEjercicioRealizado()
    }
}