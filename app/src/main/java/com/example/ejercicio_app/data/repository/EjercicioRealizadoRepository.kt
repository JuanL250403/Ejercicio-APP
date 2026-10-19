package com.example.ejercicio_app.data.repository

import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.local.dao.EjercicioRealizadoDao
import com.example.ejercicio_app.data.mapper.toUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.ui.EjercicioRealizadoUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EjercicioRealizadoRepository @Inject constructor(
    private val ejercicioRealizadoDao: EjercicioRealizadoDao
) {
    fun obtenerEjercicio(id: String): Flow<Recurso<EjercicioRealizadoUiModel>> = flow {
        emit(Recurso.Cargando)

        val ejercicio = ejercicioRealizadoDao.obtenerEjercicioRealizado(id)

        if (ejercicio != null) {
            emit(Recurso.Exito(ejercicio.toUiModel()))
        } else  {
            emit(Recurso.Error(R.string.ejercicio_inexistente))
        }
    }
}