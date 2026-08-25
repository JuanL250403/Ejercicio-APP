package com.example.ejercicio_app.data

import java.time.LocalDate

data class UsuarioRegistro(
    val nombre: String,

    val correo: String,

    val contrasenia: String,

    val fechaNacimiento: String,

    val peso: Double,

    val altura: Int
)
