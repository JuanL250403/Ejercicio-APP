package com.example.ejercicio_app.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthInterceptorModule {

    @Provides
    @Singleton
    fun provideAuthClient(
        tkManager: TokenManager
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tkManager.obtenerToken()))
            .build()
    }
}