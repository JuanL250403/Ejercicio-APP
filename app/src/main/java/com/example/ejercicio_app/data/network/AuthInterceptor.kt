package com.example.ejercicio_app.data.network

import android.util.Log
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val tkManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal = chain.request()
        Log.d("jwt", tkManager.obtenerToken())
        val requestHeaderAuth = requestOriginal.newBuilder().header("Authorization", "Bearer ${tkManager.obtenerToken()}").build()

        return chain.proceed(requestHeaderAuth)
    }
}