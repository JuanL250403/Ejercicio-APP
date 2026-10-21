package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.RutinaActualRepository
import com.example.ejercicio_app.data.ui.DetalleRutinaActualUiModel
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import com.example.ejercicio_app.utils.EstadoAppService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AgregarRutinaActualViewModel @Inject constructor(
    private val rutinaActualRepository: RutinaActualRepository,
    private val estadoAppService: EstadoAppService
) : ViewModel() {
    val nombre: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    var ejercicios: List<EjercicioActualUiModel> = emptyList()

    var parteAnatomica: ParteAnatomica? = null

    val _errorNombre: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    val errorNombre: LiveData<Int> get() = _errorNombre

    val _errorParteAnatomcia: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    val errorParteAnatomica: LiveData<Int> get() = _errorParteAnatomcia

    val _errorEjercicios: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    val errorEjercicios: LiveData<Int> get() = _errorEjercicios

    fun setNombre(nombre: String) {
        this.nombre.value = nombre
        validarNombre()
    }

    val _guardada: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }


    val guardada: LiveData<Boolean> get() = _guardada

    private fun validarNombre(): Boolean {
        if (nombre.value.isNullOrBlank()) {
            _errorNombre.value = R.string.nombre_rutina_vacio
            return false
        }

        _errorNombre.value = 0
        return true
    }

    private fun validarParteAnatomica(): Boolean {
        if(parteAnatomica == null) {
            _errorParteAnatomcia.value = R.string.parte_anatomica_vacia
            return false
        }

        _errorParteAnatomcia.value = 0
        return true
    }

    private fun validarEjercicios(): Boolean {
        if(ejercicios.isEmpty()){
            _errorEjercicios.value = R.string.ejercicios_vacios
            return false
        }

        _errorEjercicios.value = 0
        return true
    }

    private fun datosValidos(): Boolean {
        return validarNombre() and validarParteAnatomica() and validarEjercicios()
    }

    fun guardarRutina() {
        if(!datosValidos())  return

        val detalles = ejercicios.map { e -> DetalleRutinaActualUiModel(ejercicio = e) }
        val rutina = RutinaActualUiModel(
            nombre = nombre.value ?: "",
            detalle = detalles,
            parteAnatomica = parteAnatomica?.codigo ?: 0
        )

        viewModelScope.launch {
            rutinaActualRepository.guardarRutina(rutina).collect { r ->
                when(r) {
                    is Recurso.Cargando -> estadoAppService.estaCargando()
                    is Recurso.Exito -> {
                        _guardada.value = true
                        estadoAppService.noEstaCargando()
                    }
                    is Recurso.Error -> estadoAppService.noEstaCargando()
                    else -> null
                }
            }
        }

    }
}