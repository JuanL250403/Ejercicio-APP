package com.example.ejercicio_app.data.repository

import android.util.Log
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.local.dao.DiasSemanaDao
import com.example.ejercicio_app.data.mapper.toEntity
import com.example.ejercicio_app.data.mapper.toRequest
import com.example.ejercicio_app.data.mapper.toUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.network.services.DiaSemanaService
import com.example.ejercicio_app.data.ui.DiaSemanaAgregarUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaDetalleUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaUiModel
import com.example.ejercicio_app.utils.EstadoConexionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class DiasSemanaRepository @Inject constructor(
    private val diasSemanaDao: DiasSemanaDao,
    private val estadoConexionService: EstadoConexionService,
    private val diasSemanaService: DiaSemanaService,
    private val rutinaActualRepository: RutinaActualRepository
) {

    fun obtenerDiasSemana(): Flow<Recurso<List<DiaSemanaUiModel>>> = flow {
        emit(Recurso.Cargando)
        obtenerDiasSemanaRemotas()

        val dias = diasSemanaDao.obtenerDiasSemana()

        emit(Recurso.Exito(dias.toUiModel()))
    }

    fun obtenerRutinaDeDia(): Flow<Recurso<DiaSemanaDetalleUiModel>> = flow {
        emit(Recurso.Cargando)

        obtenerDiasSemanaRemotas()

        val dia = diasSemanaDao.obtenerRutinaDeDia(LocalDate.now().dayOfWeek.value)

        if (dia == null) {
            emit(Recurso.Error(R.string.rutina_inexistente))
        } else {
            emit(Recurso.Exito(dia.toUiModel()))
        }
    }

    fun obtenerDiaSemana(id: String): Flow<Recurso<DiaSemanaDetalleUiModel>> = flow {
        emit(Recurso.Cargando)
        obtenerDiasSemanaRemotas()

        val dia = diasSemanaDao.obtenerDiaSemanaDetalle(id)

        if (dia == null) {
            emit(Recurso.Error(R.string.rutina_inexistente))
        } else {
            emit(Recurso.Exito(dia.toUiModel()))
        }
    }

    fun guardarDiaSemana(diaSemana: DiaSemanaAgregarUiModel): Flow<Recurso<Int>> = flow {
        emit(Recurso.Cargando)
        val diaSemanaEntity = diaSemana.toEntity()

        diasSemanaDao.guardarDiaSemana(diaSemanaEntity)

        rutinaActualRepository.enviarRutinas()
        enviarDiasSemana()

        emit(Recurso.Exito(1))
    }

    fun eliminarDiaSemana(diaSemanaId: String): Flow<Recurso<Int>> = flow {
        emit(Recurso.Cargando)
        diasSemanaDao.eliminarDiaSemana(diaSemanaId)

        rutinaActualRepository.enviarRutinas()
        enviarDiasSemana()


        emit(Recurso.Exito(1))
    }

    suspend fun obtenerDiasSemanaRemotas() {

        if(!estadoConexionService.estaConectado.value!!) return

        try {

            val respuesta = diasSemanaService.obtenerDiasSemana()

            if (respuesta.isSuccessful && !respuesta.body().isNullOrEmpty()) {
                diasSemanaDao.guardarDiasSemana(respuesta.body()?.toEntity()!!)
                Log.d("semana", respuesta.body().toString())
            }


        } catch (e: Exception) {
            Log.d("semana", e.message.toString())
        }
    }

    suspend fun enviarDiasSemana() {

        if(!estadoConexionService.estaConectado.value!!) return

        try {
            val diasSemanaDb = diasSemanaDao.obtenerDiasSemana()

            diasSemanaService.sincronizarRutinas(diasSemanaDb.toRequest())

        } catch (e: Exception) {
            Log.d("semana", e.message.toString())
        }
    }
}