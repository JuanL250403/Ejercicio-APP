package com.example.ejercicio_app.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
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
            .addInterceptor(AuthInterceptor(tkManager))
            .connectTimeout(2, TimeUnit.SECONDS)
            .build()
    }
}