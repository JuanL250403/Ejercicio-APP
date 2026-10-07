package com.example.ejercicio_app.data.repository

import android.util.Log
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.local.dao.RutinaActualDao
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.relations.RutinaActualDetalle
import com.example.ejercicio_app.data.mapper.toEntity
import com.example.ejercicio_app.data.mapper.toRequest
import com.example.ejercicio_app.data.mapper.toUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.network.services.RutinaActualService
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RutinaActualRepository @Inject constructor(
    private val rutinaActualService: RutinaActualService,
    private val rutinaActualDao: RutinaActualDao
) {

    fun obtenerRutinas(nombre: String? = ""): Flow<Recurso<List<RutinaActualUiModel>>> = flow {
        emit(Recurso.Cargando)

        try {
            obtenerRutinasRemotas()
        }
        catch (e: Exception) {
            Log.d("excpecion", e.message.toString())
        }

        var rutinasBd: List<RutinaActualUiModel>

        if(!nombre.isNullOrBlank()) {
            rutinasBd = rutinaActualDao.obtenerRutinasDetalleNombre(nombre).toUiModel()
        } else {
            rutinasBd = rutinaActualDao.obtenerRutinasDetalle().toUiModel()
        }

        emit(Recurso.Exito(rutinasBd))

    }

    fun obtenerRutina(id: String): Flow<Recurso<RutinaActualUiModel>> = flow {
        emit(Recurso.Cargando)
        val rutina = rutinaActualDao.obtenerRutinaDetalle(id)

        if (rutina == null) {
            emit(Recurso.Error(R.string.rutina_inexistente))
        } else {

            emit(Recurso.Exito(rutina!!.toUiModel()))
        }

    }

    fun guardarRutina(rutina: RutinaActualUiModel): Flow<Recurso<Int>> = flow {

        val rutinaEntity = rutina.toEntity()

        val ejercicios = rutina.detalle.map { d -> d.ejercicio.toEntity() }

        val detalles = ejercicios.map { e ->
            DetalleRutinaActualEntity(
                rutinaActualId = rutinaEntity.id,
                ejercicioActualId = e.id
            )
        }

        rutinaActualDao.guardarRutina(rutinaEntity, detalles, ejercicios)

        enviarRutinas()

        emit(Recurso.Exito(1))

    }

    fun eliminarRutina(id: String): Flow<Recurso<Int>> = flow {
        val rutinaEliminar = rutinaActualDao.obtenerRutinaDetalle(id)

        val detallesIds = rutinaEliminar!!.detalles.map { d -> d.detalle.id }

        val ejerciciosIds = rutinaEliminar!!.detalles.map { d -> d.ejercicio.ejercicio.id }

        try {

            rutinaActualDao.eliminarRutina(rutinaEliminar!!.rutina.id, detallesIds, ejerciciosIds)


            enviarRutinas()

        } catch (e: Exception) {
            Log.d("eliminar", e.message.toString())
        }

        emit(Recurso.Exito(1))
    }

    suspend fun obtenerRutinasRemotas(): Flow<Recurso<Int>> = flow {

        try {

            val respuesta = rutinaActualService.obtenerRutinasActuales(1)

            if (respuesta.isSuccessful && respuesta.body() != null) {
                val rutinas = respuesta.body() ?: listOf()

                var detalles: MutableList<DetalleRutinaActualEntity> = mutableListOf()

                var ejercicios: MutableList<EjercicioActualEntity> = mutableListOf()

                rutinas.forEach { r ->
                    ejercicios.addAll(r.detalle.map { d -> d.ejercicio }.toEntity())
                    detalles.addAll(r.detalle.toEntity(r.id))
                }

                rutinaActualDao.sincronizarRutina(
                    rutinas.toEntity(),
                    ejercicios.toList(),
                    detalles.toList()
                )

            }
        } catch (e: Exception) {
            emit(Recurso.Error(1))
        }

        emit(Recurso.Exito(1))

    }

    suspend fun enviarRutinas() {
        try {

            val rutinasDb = rutinaActualDao.obtenerRutinasDetalle()

            rutinaActualService.sincronizarRutinas(1, rutinasDb.toRequest())

        } catch (e: Exception) {
            Log.d("rutina", e.message.toString())
        }
    }


}