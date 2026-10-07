package com.example.ejercicio_app.data.repository

import com.example.ejercicio_app.data.local.dao.EjercicioActualDao
import com.example.ejercicio_app.data.mapper.toUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EjercicioActualRepository @Inject constructor(private val ejercicioActualDao: EjercicioActualDao) {

    fun obtenerEjercicio(id: String): Flow<Recurso<EjercicioActualUiModel>> = flow {
        val ejercicioActual = ejercicioActualDao.obtenerEjercicioActual(id)

        emit(Recurso.Exito(ejercicioActual.toUiModel()))
    }
}