package com.example.ejercicio_app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.auth0.jwt.JWT
import com.example.ejercicio_app.data.network.TokenManager
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.SincronizacionRepository
import com.example.ejercicio_app.databinding.ActivityInicioSesionBinding
import com.example.ejercicio_app.models.InicioSesionViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class InicioSesion : AppCompatActivity() {
    @Inject
    lateinit var tkManger: TokenManager

    @Inject
    lateinit var sincronizacionRepository: SincronizacionRepository

    private lateinit var binding: ActivityInicioSesionBinding

    private val vm: InicioSesionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sesionActiva()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etCorreo.doAfterTextChanged { i ->
            vm.setCorreo(i.toString())
        }

        binding.etContrasena.doAfterTextChanged { i ->
            vm.setContrasnea(i.toString())
        }

        binding.registrarse.setOnClickListener {
            registrarse()
        }

        binding.btnEntrar.setOnClickListener {
            vm.iniciarSesion()
        }

        vm.estadoValidado.observe(this, { estado ->
            Toast.makeText(this@InicioSesion, getString(estado), Toast.LENGTH_SHORT).show()
        })

        vm.validado.observe(this, { validado ->
            if (validado) {

                lifecycleScope.launch {
                    sincronizacionRepository.obtenerDatosRemotos().collect { r ->
                        when (r) {
                            is Recurso.Exito -> {
                                val intent = Intent(this@InicioSesion, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else -> null
                        }
                    }

                }



            }
        })

        vm.errorCorreo.observe(this, { e ->
            if (e.equals(0)) removerValidacionCampo(binding.ltCorreo)
            else setValidacionCampo(binding.ltCorreo, e)
        })

        vm.erroContrasena.observe(this, { e ->
            if (e.equals(0)) removerValidacionCampo(binding.ltContrasnea)
            else setValidacionCampo(binding.ltContrasnea, e)
        })

    }

    fun registrarse() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
        finish()
    }

    fun setValidacionCampo(input: TextInputLayout, error: Int) {
        input.isErrorEnabled = true
        input.setError(getString(error))
    }

    fun removerValidacionCampo(input: TextInputLayout) {
        input.error = null
        input.isErrorEnabled = false
    }

    fun sesionActiva(): Boolean {

        try {

            val tokenRaw = tkManger.obtenerToken()

            if (tokenRaw.isNullOrBlank() || tokenRaw.split(".").size != 3) {
                return false
            }

            val token = JWT.decode(tokenRaw)
            val expiracion = token.expiresAt ?: return false

            return expiracion.after(Date())

        } catch (e: Exception) {
            return false
        }

    }

}