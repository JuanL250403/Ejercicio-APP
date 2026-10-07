package com.example.ejercicio_app.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("authToken") val authToken: String
)