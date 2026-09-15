package com.example.ejercicio_app.data.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("authToken") val authToken: String
)