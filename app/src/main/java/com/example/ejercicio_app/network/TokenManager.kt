package com.example.ejercicio_app.network

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val JWT_KEY = "jwt"

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "jwt_token",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit();

    fun obtenerToken(): String {
        return sharedPreferences.getString(JWT_KEY, null) ?: ""
    }

    fun guardarToken(token: String) {
        editor.apply {
            putString(JWT_KEY, token)
        }
    }

    fun eliminarToken() {
        editor.apply {
            putString(JWT_KEY, null)
        }
    }
}