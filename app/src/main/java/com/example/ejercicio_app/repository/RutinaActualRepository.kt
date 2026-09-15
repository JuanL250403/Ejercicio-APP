package com.example.ejercicio_app.repository

import android.util.Log
import com.example.ejercicio_app.data.Recurso
import com.example.ejercicio_app.data.response.RutinaActualResponse
import com.example.ejercicio_app.network.services.RutinaActualService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RutinaActualRepository @Inject constructor(
    private val rutinaActualService: RutinaActualService
) {

    fun obtenerRutinas(): Flow<Recurso<List<RutinaActualResponse>>> = flow {
        emit(Recurso.Cargando)

        try {
            val respuesta = rutinaActualService.obtenerRutinasActuales(1)

            if(respuesta.isSuccessful && respuesta.body() != null) {
                val rutinas = respuesta.body() ?: listOf()

                emit(Recurso.Exito(rutinas))
            }


        } catch (e: Exception) {
            emit(Recurso.Error(1))
        }
    }
}