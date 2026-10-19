package com.example.ejercicio_app.data.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val JWT_KEY = "jwt"
    private val PREFS_FILE_NAME = "jwt_token"
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = try {
            inicializarEncryptedPrefs(context)
        } catch (e: Exception) {
            // ⚠️ Captura el AEADBadTagException cuando las llaves del Keystore se corrompen
            Log.e("TokenManager", "Error con Keystore/Cifrado. Limpiando preferencias corruptas.", e)

            // Borramos físicamente el archivo XML corrupto que Android ya no puede descifrar
            context.deleteSharedPreferences(PREFS_FILE_NAME)

            // Reintentamos crearlo desde cero con un juego de llaves limpio
            inicializarEncryptedPrefs(context)
        }
    }

    // Método privado auxiliar para construir las SharedPreferences Encriptadas
    private fun inicializarEncryptedPrefs(ctx: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(ctx)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            ctx,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Cambiado a String? para evitar que devuelva un string vacío "" y cause fallos en el JWT
    fun obtenerToken(): String {
        return sharedPreferences.getString(JWT_KEY, null) ?: ""
    }

    fun guardarToken(token: String) {
        sharedPreferences.edit().putString(JWT_KEY, token).apply()
    }

    fun eliminarToken() {
        sharedPreferences.edit().remove(JWT_KEY).apply()
    }
}
