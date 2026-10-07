package com.example.ejercicio_app.activities

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.repository.RutinaActualRepository
import com.example.ejercicio_app.databinding.ActivityMainBinding
import com.example.ejercicio_app.models.MainConexionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {

    @Inject
    lateinit var rutinaActualRepository: RutinaActualRepository

    private lateinit var binding: ActivityMainBinding

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            vm.setEsstadoConexion(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            vm.setEsstadoConexion(false)
        }
    }
    val vm: MainConexionViewModel by viewModels()

    private lateinit var conectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        configurarNavegacion()

        configurarCallBackConexion()

        vm.estaConectado.observe(this) { ec ->
            Toast.makeText(this, "La conexion es $ec", Toast.LENGTH_SHORT).show()

            if(ec) {
                lifecycleScope.launch {
                    rutinaActualRepository.enviarRutinas()
                    rutinaActualRepository.obtenerRutinasRemotas()
                }
            }
        }
    }

    fun configurarNavegacion() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.navBottom.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, bundle ->
            when(destination.id) {
                R.id.inicioFragment -> binding.navBottom.visibility = View.VISIBLE
                R.id.rutinasActualesFragment -> binding.navBottom.visibility = View.VISIBLE
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navController = navHostFragment.navController

        return  navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun configurarCallBackConexion() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        conectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        conectivityManager.registerNetworkCallback(networkRequest, networkCallBack)

    }

    override fun onDestroy() {
        super.onDestroy()
        conectivityManager.unregisterNetworkCallback(networkCallBack)
    }
}