package com.example.ejercicio_app.data.network.dto

sealed class Recurso<out T> {
    object Cargando : Recurso<Nothing>()
    data class Exito<out T>(val data: T): Recurso<T>()
    data class Error(val mensaje: Int): Recurso<Nothing>()
}