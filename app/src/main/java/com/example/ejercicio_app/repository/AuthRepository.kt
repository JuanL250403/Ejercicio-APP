package com.example.ejercicio_app.repository

import android.util.Log
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.Recurso
import com.example.ejercicio_app.data.request.UsuarioInicioSesion
import com.example.ejercicio_app.data.request.UsuarioRegistro
import com.example.ejercicio_app.network.TokenManager
import com.example.ejercicio_app.network.services.AuthService
import com.example.ejercicio_app.network.services.UsuarioService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val usuarioService: UsuarioService,
    private val tkManager: TokenManager
) {

    fun iniciarSesion(usuario: UsuarioInicioSesion): Flow<Recurso<Int>> = flow {

        emit(Recurso.Cargando)

        try {
            val respuesta = authService.iniciarSesion(usuario)

            if (respuesta.isSuccessful && respuesta.body() != null) {
                val token = respuesta.body()?.authToken ?: ""

                tkManager.guardarToken(token)

                emit(Recurso.Exito(R.string.sesion_iniciada))

                Log.d("jwt", tkManager.obtenerToken())

            } else  {
                emit(Recurso.Error(R.string.credenciales_invalidas))
            }

        } catch (e: Exception) {
            Log.d("inicio", e.message.toString())
        }
    }

    fun registrarse(usuario: UsuarioRegistro): Flow<Recurso<Int>> = flow {
        emit(Recurso.Cargando)

        try {
            val respuesta = usuarioService.registrarUsuario(usuario)

            if(respuesta.isSuccessful) {
                emit(Recurso.Exito(R.string.usuario_registrado))
            } else if(respuesta.code() == 403) {
                emit(Recurso.Error(R.string.correo_registrado))
            }
        } catch (e: Exception) {

        }
    }
}