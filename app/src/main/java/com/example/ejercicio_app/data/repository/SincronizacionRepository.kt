package com.example.ejercicio_app.data.repository

import androidx.room3.RoomDatabase
import androidx.room3.useWriterConnection
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.ejercicio_app.data.local.AppDatabase
import com.example.ejercicio_app.data.local.dao.DatabaseDao
import com.example.ejercicio_app.data.network.dto.Recurso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SincronizacionRepository @Inject constructor(
    private val database: AppDatabase,
    private val databaseDao: DatabaseDao,
    private val diasSemanaRepository: DiasSemanaRepository,
    private val rutinaActualRepository: RutinaActualRepository,
    private val rutinaRealizadaRepository: RutinaRealizadaRepository
) {

    suspend fun enviarDatos() {
        rutinaActualRepository.enviarRutinas()
        rutinaRealizadaRepository.enviarRutinas()
        diasSemanaRepository.enviarDiasSemana()
    }

    fun obtenerDatosRemotos(): Flow<Recurso<Unit>> = flow {
        emit(Recurso.Cargando)

        rutinaActualRepository.obtenerRutinasRemotas()
        rutinaRealizadaRepository.obtenerRutinasRemotas()
        diasSemanaRepository.obtenerDiasSemanaRemotas()

        emit(Recurso.Exito(Unit))

    }

    suspend fun eliminarDatos() {
        withContext(Dispatchers.IO) {
            databaseDao.eliminarDatosUsuario()
        }
    }

}