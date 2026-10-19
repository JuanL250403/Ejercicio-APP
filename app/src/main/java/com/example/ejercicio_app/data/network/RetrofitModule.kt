package com.example.ejercicio_app.data.network

import com.example.ejercicio_app.BuildConfig
import com.example.ejercicio_app.data.network.services.AuthService
import com.example.ejercicio_app.data.network.services.DiaSemanaService
import com.example.ejercicio_app.data.network.services.RutinaActualService
import com.example.ejercicio_app.data.network.services.RutinaRealizadaService
import com.example.ejercicio_app.data.network.services.UsuarioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    @Qualifier
    annotation class AuthRetrofit

    @Qualifier
    annotation class PublicRetrofit

    @AuthRetrofit
    @Provides
    @Singleton
    fun provideAuthRetrofit(authClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @PublicRetrofit
    @Provides
    @Singleton
    fun providePublicRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(@PublicRetrofit retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUsuarioService(@PublicRetrofit retrofit: Retrofit): UsuarioService = retrofit.create(UsuarioService::class.java)

    @Provides
    @Singleton
    fun provideRutinaActualService(@AuthRetrofit retrofit: Retrofit): RutinaActualService = retrofit.create(
        RutinaActualService::class.java)

    @Provides
    @Singleton
    fun provideDiaSemanaService(@AuthRetrofit retrofit: Retrofit): DiaSemanaService = retrofit.create(
        DiaSemanaService::class.java)

    @Provides
    @Singleton
    fun provideRutinaRealizadaService(@AuthRetrofit retrofit: Retrofit): RutinaRealizadaService = retrofit.create(
        RutinaRealizadaService::class.java)
}