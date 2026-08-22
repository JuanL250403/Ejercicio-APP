package com.example.ejercicio_app.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ejercicio_app.databinding.ActivityRegistroBinding

class Registro : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}