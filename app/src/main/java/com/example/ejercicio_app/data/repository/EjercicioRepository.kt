package com.example.ejercicio_app.data.repository

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.local.dao.EjercicioDataSetDao
import com.example.ejercicio_app.data.mapper.toUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.ui.EjercicioDataSetUiModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EjercicioRepository @Inject constructor(private val ejercicioDataSetDao: EjercicioDataSetDao) {

     fun obtenerEjercicios(): Flow<Recurso<List<EjercicioDataSetUiModel>>> = flow {

         emit(Recurso.Exito(ejercicioDataSetDao.obtenerEjerciciosDataset().toUiModel()))
    }

    fun obtenerEjercicio(id: String): Flow<Recurso<EjercicioDataSetUiModel>> = flow {
        val ejercicio = ejercicioDataSetDao.obtenerEjercicioDataset(id)

        if(ejercicio == null) {
            emit(Recurso.Error(R.string.ejercicio_inexistente))
        } else {
            emit(Recurso.Exito(ejercicio.toUiModel()))
        }


    }

}