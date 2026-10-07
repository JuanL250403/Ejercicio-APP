package com.example.ejercicio_app.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.ui.EjercicioDataSetUiModel
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.data.ParteCuerpo
import com.example.ejercicio_app.data.PartesCuerpo
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.EjercicioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.isBlank

@HiltViewModel
class BuscarEjercicioAgregarViewModel @Inject constructor
    (private val ejercicioRepository: EjercicioRepository) : ViewModel() {

    val busqueda: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val categoria: MutableLiveData<String> by lazy {
        MutableLiveData<String>("todo")
    }

    val parteAnatomica: MutableLiveData<ParteAnatomica> by lazy {
        MutableLiveData(ParteAnatomica.TREN_SUPERIOR)
    }

    private var ejerciciosDataSet: List<EjercicioDataSetUiModel> = emptyList()

    val ejercicios: MutableLiveData<List<EjercicioDataSetUiModel>> by lazy {
        MutableLiveData()
    }

    val categorias: MutableLiveData<List<ParteCuerpo>> by lazy {
        MutableLiveData(emptyList())
    }

    fun setCategoria(categoria: String) {
        this.categoria.value = categoria
    }

    fun setParteAnatomica(parte: ParteAnatomica) {
        parteAnatomica.value = parte
    }

    fun setBusqueda(busqueda: String) {
        this.busqueda.value = busqueda
    }

    init {
        cargarEjercicios()
        cargarCategorias()
    }

    fun cargarEjercicios() {
        viewModelScope.launch {

            ejercicioRepository.obtenerEjercicios().collect { r ->
                when(r) {
                    is Recurso.Exito -> {
                        ejerciciosDataSet = r.data
                        ejercicios.value = r.data
                    }
                    else -> null
                }
            }
        }


    }

    fun cargarEjercicioFiltrados() {
        ejercicios.value = filtrarEjercicios()
    }

    fun cargarCategorias() {
        if (parteAnatomica.value == ParteAnatomica.TREN_SUPERIOR) {
            categorias.value = PartesCuerpo.trenSuperior
        } else {
            categorias.value = PartesCuerpo.trenInferior
        }
    }

     fun buscarEjercicios() {

        val ejerciciosFiltrados = filtrarEjercicios()

        if (busqueda.value.toString().isBlank()) {

            ejercicios.value = ejerciciosFiltrados
        } else {
            ejercicios.value = ejerciciosFiltrados.filter { e -> e.name.contains(busqueda.value.toString().trim()) }

        }
    }

    private fun filtrarEjercicios(): List<EjercicioDataSetUiModel> {
        val categoriasActuales = categorias.value ?: emptyList()


        val ejerciciosParteAnato = ejerciciosDataSet.filter { ejercicio ->
            categoriasActuales.any { c -> c.nombreIngles.lowercase() == ejercicio.category }
        }

        if (categoria.value == "todo") {
            return ejerciciosParteAnato
        } else {
            return ejerciciosParteAnato.filter { e -> e.category == categoria.value?.lowercase() }
        }

    }
}
