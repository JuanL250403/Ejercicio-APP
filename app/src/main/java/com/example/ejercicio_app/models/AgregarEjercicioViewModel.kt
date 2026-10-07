package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.ui.EjercicioDataSetUiModel
import com.example.ejercicio_app.data.repository.EjercicioRepository
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class AgregarEjercicioViewModel @Inject constructor(private val ejercicioRepository: EjercicioRepository) :
    ViewModel()  {
    val series: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    private val _errorSeries: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }

    val erroSeries: LiveData<Int> get() = _errorSeries

    val ejercicio: MutableLiveData<EjercicioDataSetUiModel> by lazy {
        MutableLiveData()
    }

    val descanso: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    private val _errorDescanso: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }

    val errorDescanso: LiveData<Int> get() = _errorDescanso

    val reps: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    private val _errorReps: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }

    val errorReps: LiveData<Int> get() = _errorReps

    val peso: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    private val _errorPeso: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }

    val errorPeso: LiveData<Int> get() = _errorPeso


    fun setSeries(series: Int) {
        this.series.value = series
        validarSeries()
    }

    fun setDescanso(descanso: Int) {
        this.descanso.value = descanso
        validarDescanso()
    }

    fun setReps(reps: Int) {
        this.reps.value = reps
        validarReps()
    }

    fun setPeso(peso: Int) {
        this.peso.value = peso
        validarPeso()
    }


    fun cargarEjercicio(ejercicioId: String) {
        viewModelScope.launch {

                ejercicioRepository.obtenerEjercicio(ejercicioId).collect { r ->
                    when(r) {
                        is Recurso.Exito -> ejercicio.value = r.data
                        else -> null
                    }
                }


        }
    }

    fun datosValidos(): Boolean {
        return validarDescanso() and validarSeries() and validarPeso() and validarReps()
    }

    fun crearEjercicio(): EjercicioActualUiModel {
        return EjercicioActualUiModel(
            id = ejercicio.value?.id!!,
            series = series.value!!,
            nombre = ejercicio.value?.name!!,
            repeticiones = reps.value!!,
            descanso = descanso.value!!,
            tiempo = 0,
            ejercicio = ejercicio.value!!,
            peso = peso.value!!
        )
    }

    private fun validarSeries(): Boolean {
        val seriesValue = series.value ?: 0
        if (seriesValue <= 0) {
            _errorSeries.value = R.string.series_invalida
            return false
        }

        _errorSeries.value = 0
        return true
    }

    private fun validarDescanso(): Boolean {
        val descansoValue = descanso.value ?: 0
        if (descansoValue <= 0) {
            _errorDescanso.value = R.string.descanso_invalido
            return false
        }

        _errorDescanso.value = 0
        return true
    }

    private fun validarReps(): Boolean {
        val repsValue = reps.value ?: 0
        if (repsValue <= 0) {
            _errorReps.value = R.string.reps_invalidas
            return false
        }

        _errorReps.value = 0
        return true
    }

    private fun validarPeso(): Boolean {

        if(peso.value == null) {
            _errorPeso.value = R.string.peso_invalido
            return false
        }

        val pesoValue = peso.value ?: 0

        if (pesoValue < 0) {
            _errorPeso.value = R.string.peso_invalido
            return false
        }

        _errorPeso.value = 0
        return true
    }

}