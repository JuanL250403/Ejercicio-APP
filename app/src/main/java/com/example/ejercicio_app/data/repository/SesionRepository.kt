package com.example.ejercicio_app.data.repository

import android.util.Log
import com.auth0.jwt.JWT
import com.example.ejercicio_app.data.network.TokenManager
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.ui.UsuarioUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

class SesionRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val sincronizacionRepository: SincronizacionRepository
) {

    fun obtenerUsuario(): UsuarioUiModel? {
        try {

            val tokenRaw = tokenManager.obtenerToken()

            val tokem = JWT.decode(tokenRaw)

            val anioNacimiento = LocalDate.parse(tokem.getClaim("fechaNacimiento").asString() ?: "")

            return UsuarioUiModel(
                nombre = tokem.getClaim("nombre").asString() ?: "",
                peso = tokem.getClaim("peso").asInt() ?: 0,
                altura = tokem.getClaim("altura").asInt() ?: 0,
                edad = Period.between(anioNacimiento, LocalDate.now()).years
            )


        } catch (e: Exception) {
            Log.d("usuario", e.message.toString())
        }

        return null
    }

    fun cerrarSesion(): Flow<Recurso<Unit>> = flow {
        emit(Recurso.Cargando)
        tokenManager.eliminarToken()
        sincronizacionRepository.eliminarDatos()
        emit(Recurso.Exito(Unit))
    }
}