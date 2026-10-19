package com.example.ejercicio_app.data.repository

import android.util.Log
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.local.dao.DiasSemanaDao
import com.example.ejercicio_app.data.local.dao.RutinaRealizadaDao
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.DetalleRutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.DiaSemanaRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioRealizadoEntity
import com.example.ejercicio_app.data.mapper.toEntity
import com.example.ejercicio_app.data.mapper.toRequest
import com.example.ejercicio_app.data.mapper.toUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.network.services.RutinaRealizadaService
import com.example.ejercicio_app.data.ui.RutinaRealizadaUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RutinaRealizadaRepository @Inject constructor(
    private val rutinaRealizadaDao: RutinaRealizadaDao,
    private val rutinaRealizadaService: RutinaRealizadaService,
    private val diaSemanaDao: DiasSemanaDao,
    private val diasSemanaRepository: DiasSemanaRepository
) {

    fun obtenerRutinas(): Flow<Recurso<List<RutinaRealizadaUiModel>>> = flow {
        emit(Recurso.Cargando)

        obtenerRutinasRemotas()

        val rutinas = rutinaRealizadaDao.obtenerRutinasDetalle().toUiModel()

        emit(Recurso.Exito(rutinas))
    }

    fun guardarRutina(rutina: RutinaRealizadaUiModel, diaSemanaId: String): Flow<Recurso<Unit>> =
        flow {
            emit(Recurso.Cargando)

            val rutinaEntity = rutina.toEntity()

            val ejercicios = rutina.detalle.map { d -> d.ejercicio.toEntity() }

            val detalles = ejercicios.map { e ->
                DetalleRutinaRealizadaEntity(
                    rutinaRealizadaId = rutinaEntity.id,
                    ejercicioRealizadoId = e.id
                )
            }

            val diaSemana = diaSemanaDao.obtenerDiaSemana(diaSemanaId)

            if (diaSemana != null) {
                val diaSemanaModificado = diaSemana.copy(estado = true)

                diaSemanaDao.guardarDiaSemana(diaSemanaModificado)

                diasSemanaRepository.enviarDiasSemana()
            } else {
                emit(Recurso.Error(R.string.dia_inexistente))
            }


            rutinaRealizadaDao.guardarRutina(
                rutina = rutinaEntity,
                ejercicios = ejercicios,
                detalles = detalles
            )

            enviarRutinas()

            emit(Recurso.Exito(Unit))
        }

    fun obtenerRutina(id: String): Flow<Recurso<RutinaRealizadaUiModel>> = flow {
        emit(Recurso.Cargando)
        obtenerRutinasRemotas()

        val rutina = rutinaRealizadaDao.obtenerRutina(id)

        if (rutina == null) {
            emit(Recurso.Error(R.string.rutina_inexistente))
        } else {
            emit(Recurso.Exito(rutina.toUiModel()))
        }
    }

    suspend fun obtenerRutinasRemotas() {

        try {

            val respuesta = rutinaRealizadaService.obtenerRutinasRealizadas(1)

            if (respuesta.isSuccessful && respuesta.body() != null) {
                val rutinas = respuesta.body() ?: listOf()

                var detalles: MutableList<DetalleRutinaRealizadaEntity> = mutableListOf()

                var ejercicios: MutableList<EjercicioRealizadoEntity> = mutableListOf()

                rutinas.forEach { r ->
                    ejercicios.addAll(r.detalle.map { d -> d.ejercicio }.toEntity())
                    detalles.addAll(r.detalle.toEntity(r.id))
                }

                rutinaRealizadaDao.guardarRutinas(
                    rutinas.toEntity(),
                    ejercicios.toList(),
                    detalles.toList()
                )

            }
        } catch (e: Exception) {
            Log.d("rutinas", e.message.toString())
        }

    }

    suspend fun enviarRutinas() {

        try {

            val rutinasDb = rutinaRealizadaDao.obtenerRutinasDetalle()

            rutinaRealizadaService.sincronizarRutinas(1, rutinasDb.toRequest())

        } catch (e: Exception) {
            Log.d("rutinas", e.message.toString())
        }

    }
}