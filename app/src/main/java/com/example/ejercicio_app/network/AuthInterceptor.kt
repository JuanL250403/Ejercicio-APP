package com.example.ejercicio_app.network

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal = chain.request()
        val requestHeaderAuth = requestOriginal.newBuilder().header("Authorization", "Bearer ${token}").build()

        return chain.proceed(requestHeaderAuth)
    }
}