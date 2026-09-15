package com.example.ejercicio_app.network

import com.example.ejercicio_app.BuildConfig
import com.example.ejercicio_app.network.services.AuthService
import com.example.ejercicio_app.network.services.RutinaActualService
import com.example.ejercicio_app.network.services.UsuarioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(authClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUsuarioService(retrofit: Retrofit): UsuarioService = retrofit.create(UsuarioService::class.java)

    @Provides
    @Singleton
    fun provideRutinaActualService(retrofit: Retrofit): RutinaActualService = retrofit.create(
        RutinaActualService::class.java)
}