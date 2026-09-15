package com.example.ejercicio_app.data.request

data class UsuarioRegistro(
    val nombre: String,

    val correo: String,

    val contrasenia: String,

    val fechaNacimiento: String,

    val peso: Double,

    val altura: Int
)