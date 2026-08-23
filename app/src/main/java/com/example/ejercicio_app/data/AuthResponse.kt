package com.example.ejercicio_app.data

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("authToken") val token: String
)
