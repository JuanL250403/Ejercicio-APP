package com.example.ejercicio_app.network.services

import com.example.ejercicio_app.data.UsuarioRegistro
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {

    @POST("api/usuario")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegistro): Response<ResponseBody>
}