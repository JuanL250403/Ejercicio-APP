package com.example.ejercicio_app.data.ui

enum class ParteAnatomica(val id: Long, val parteAnatomica: String) {
    TREN_SUPERIOR(1, "Tren superior"),
    TREN_INFERIOR(2, "Tren inferior"),

    CUERPO_COMPLETO(3, "Cuerpo completo");

    val codigo get() = this.id

    val nombre get() = this.parteAnatomica

    companion object {

        fun obtenerParteAnatomica(id: Long): String? {
            return entries.firstOrNull { e -> e.id == id }?.nombre
        }
    }
}