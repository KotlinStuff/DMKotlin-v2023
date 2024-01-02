package es.javiercarrasco.myfirstgooglemaps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import es.javiercarrasco.myfirstgooglemaps.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your app.
                Log.i("PERMISOS", "Permiso concedido.")
                configMap()
            } else {
                Log.e("PERMISOS", "Explicación de la necesidad del permiso.")
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment =
            supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    // Comprueba el estado del permiso.
    private fun isPermissionGranted() = ContextCompat.checkSelfPermission(
        this, android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    // Comprueba el permiso de ubicación y recoloca el mapa según la ubicación.
    @SuppressLint("MissingPermission")
    private fun configMap() {
        when {
            (isPermissionGranted()) -> {
                // Se añade la marca en la ubicación real.
                map.isMyLocationEnabled = true
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))
                }
            }

            else -> requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Se habilitan los botones del zoom.
        map.uiSettings.isZoomControlsEnabled = true
        // Se habilita la brújula, solo aparecerá cuando se gire el mapa.
        map.uiSettings.isCompassEnabled = true
        // Se habilita el botón para centrar la ubicación actual (por defecto es true).
        map.uiSettings.isMyLocationButtonEnabled = true

        configMap()
    }
}