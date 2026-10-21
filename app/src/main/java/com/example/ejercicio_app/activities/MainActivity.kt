package com.example.ejercicio_app.activities

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateUtils
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.repository.SincronizacionRepository
import com.example.ejercicio_app.databinding.ActivityMainBinding
import com.example.ejercicio_app.utils.EstadoAppService
import com.example.ejercicio_app.utils.EstadoConexionService
import com.example.ejercicio_app.models.RealizarEntrenamientoSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sincronizacionRepository: SincronizacionRepository

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var estadoApp: EstadoAppService

    val vmRealizarEntrenamientoShared: RealizarEntrenamientoSharedViewModel by viewModels()

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            estadoConexion.setEsstadoConexion(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            estadoConexion.setEsstadoConexion(false)
        }
    }

    @Inject
    lateinit var estadoConexion: EstadoConexionService


    private lateinit var conectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        configurarNavegacion()

        configurarCallBackConexion()

        Glide.with(this).load(R.drawable.cargando).into(binding.imCargando)

        binding.ltCardTiempo.setOnClickListener {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

            val navController = navHostFragment.navController

            navController.navigate(R.id.realizarEntrenamientoFragment)
        }

        binding.btnEstado.setOnClickListener {
            vmRealizarEntrenamientoShared.setCorriendo(!vmRealizarEntrenamientoShared.corriendo.value!!)
        }

        vmRealizarEntrenamientoShared.iniciado.observe(this) { i ->
            if (i) {
                binding.ltCardTiempo.visibility = View.VISIBLE
            } else {
                binding.ltCardTiempo.visibility = View.GONE
                binding.crTiempo.base = SystemClock.elapsedRealtime()
                vmRealizarEntrenamientoShared.tiempo.value = 0
            }
        }

        vmRealizarEntrenamientoShared.corriendo.observe(this) { c ->
            if (c)
                iniciarCronometro()
            else
                pausarCronometro()

        }

        estadoConexion.estaConectado.observe(this) { ec ->

            if (ec) {
                lifecycleScope.launch {
                    sincronizacionRepository.enviarDatos()
                }
            }
        }

        estadoApp.cargando.observe(this) { c ->
            if(c) {
                binding.ltCargando.visibility = View.VISIBLE

            } else {
                binding.ltCargando.visibility = View.GONE

            }
        }
    }

    private fun iniciarCronometro() {
        binding.btnEstado.icon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_pause, null)
        binding.crTiempo.base =
            SystemClock.elapsedRealtime() - vmRealizarEntrenamientoShared.tiempoPausado.value!!
        setCronometroListener()
        binding.crTiempo.start()
    }

    private fun pausarCronometro() {
        binding.btnEstado.icon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_play, null)
        binding.crTiempo.stop()
        limpiarCronomertroListener()
        vmRealizarEntrenamientoShared.setTiempoPausado(SystemClock.elapsedRealtime() - binding.crTiempo.base)
    }

    private fun limpiarCronomertroListener() {
        binding.crTiempo.setOnChronometerTickListener(null)
    }

    private fun setCronometroListener() {
        binding.crTiempo.setOnChronometerTickListener { chrono ->
            val tiempo = (SystemClock.elapsedRealtime() - chrono.base) / 1000

            binding.crTiempo.text = DateUtils.formatElapsedTime(tiempo)

            vmRealizarEntrenamientoShared.setTiempo(tiempo)
        }
    }

    private fun configurarNavegacion() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.navBottom.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, bundle ->
            when (destination.id) {
                R.id.inicioFragment -> binding.navBottom.visibility = View.VISIBLE
                R.id.rutinasActualesFragment -> binding.navBottom.visibility = View.VISIBLE
                R.id.calendarioEntrenamientoFragment -> binding.navBottom.visibility = View.VISIBLE
                R.id.historialEntrenamientoFragment -> binding.navBottom.visibility = View.VISIBLE
                R.id.perfilFragment -> binding.navBottom.visibility = View.VISIBLE
                else -> binding.navBottom.visibility = View.GONE
            }
        }

        val appToolBar = AppBarConfiguration(
            setOf(R.id.inicioFragment, R.id.rutinasActualesFragment)
        )

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appToolBar)
    }

    override fun onSupportNavigateUp(): Boolean {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navController = navHostFragment.navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun configurarCallBackConexion() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        conectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        conectivityManager.registerNetworkCallback(networkRequest, networkCallBack)

    }

    override fun onDestroy() {
        super.onDestroy()
        conectivityManager.unregisterNetworkCallback(networkCallBack)
    }
}