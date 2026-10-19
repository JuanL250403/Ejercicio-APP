package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.mapper.toRealizadaUiModel
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.DiasSemanaRepository
import com.example.ejercicio_app.data.repository.RutinaRealizadaRepository
import com.example.ejercicio_app.data.ui.DetalleRutinaRealizadaUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaAgregarUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaDetalleUiModel
import com.example.ejercicio_app.data.ui.EjercicioRealizadoUiModel
import com.example.ejercicio_app.data.ui.RutinaRealizadaUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RealizarEntrenamientoSharedViewModel @Inject constructor(
    private val diasSemanaRepository: DiasSemanaRepository,
    private val rutinaRealizadaRepository: RutinaRealizadaRepository
) : ViewModel() {

    private val _diaSemana: MutableLiveData<DiaSemanaDetalleUiModel> by lazy {
        MutableLiveData()
    }

    val diaSemana: LiveData<DiaSemanaDetalleUiModel> get() = _diaSemana

    val corriendo: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val iniciado: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val tiempo: MutableLiveData<Long> by lazy {
        MutableLiveData(0)
    }

    val tiempoPausado: MutableLiveData<Long> by lazy {
        MutableLiveData(0)
    }

    val _guardado: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val guardado: LiveData<Boolean> get() = _guardado

    fun setTiempo(timepo: Long) {
        this.tiempo.value = timepo
    }

    fun setTiempoPausado(tiempoPausado: Long) {
        this.tiempoPausado.value = tiempoPausado
    }

    fun setCorriendo(corriendo: Boolean) {
        this.corriendo.value = corriendo
    }

    fun setIniciado(iniciado: Boolean) {
        this.iniciado.value = iniciado
    }

    fun obtenerDiaSemana(diaId: String) {
        viewModelScope.launch {
            diasSemanaRepository.obtenerDiaSemana(diaId).collect { r ->
                when(r) {
                    is Recurso.Exito -> _diaSemana.value = r.data
                    else -> null
                }
            }
        }
    }

    fun guardarRutinaRealizada() {


        val rutina = RutinaRealizadaUiModel(
            nombre = this.diaSemana.value?.rutina?.nombre!!,
            fechaRealizacion = LocalDate.now(),
            tiempoRealizacion = tiempo.value?.toInt()!!,
            parteAnatomica = this.diaSemana.value?.rutina?.parteAnatomica!!,
            diaSemana = this.diaSemana.value?.diaSemana!!,
            detalle = this.diaSemana.value?.rutina?.detalle!!.toRealizadaUiModel()
        )

        viewModelScope.launch {
            rutinaRealizadaRepository.guardarRutina(rutina, _diaSemana.value?.id!!).collect { r ->
                when(r) {
                    is Recurso.Exito -> {
                        _guardado.value = true
                        //limpiarDatos()
                    }
                    else -> null
                }
            }
        }
    }

    fun limpiarDatos() {
        _diaSemana.value = null
        tiempo.value = 0
        tiempoPausado.value = 0
        _guardado.value = false
    }

}