package es.javiercarrasco.myfirstgooglemaps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import es.javiercarrasco.myfirstgooglemaps.databinding.ActivityMainBinding
import java.io.IOException


class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMainBinding

    override fun onMyLocationButtonClick(): Boolean {
        Log.d("onMyLocationButtonClick", "Click sobre el botón ubicación!!")

        return false
    }

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
                    if (location != null) {
                        lastLocation = location
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))
                    }
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

        // Se añade el listener para el botón de ubicación.
        map.setOnMyLocationButtonClickListener(this)

        // Se añade el listener para el click sobre la ubicación.
        //map.setOnMyLocationClickListener(this)
        map.setOnMyLocationClickListener {
            Log.d("onMyLocationClick", "Mi ubicación pulsada [${it.latitude}, ${it.longitude}]")
        }

        map.setOnMapLongClickListener {
            Log.d("onMapClickListener", it.toString())
            placeMarker(it)
            map.animateCamera(CameraUpdateFactory.newLatLng(it))
        }

        map.setOnMarkerClickListener { marker ->
            Log.d("onMarkerClickListener", marker.id)
            marker.showInfoWindow()
            true
        }

        configMap()
    }

    // Método para añadir la marca en la localización indicada.
    private fun placeMarker(location: LatLng) {
        Log.d("placeMarker", "Marcador en la ubicación: $location")

        // Se crea un objeto MarkerOptions para configurar la marca.
        val markerOptions = MarkerOptions().position(location)
        markerOptions.title(getAddress(location))  // Título de la marca.
        markerOptions.snippet("Esta es mi marca")  // Descripción de la marca.

        // Color de la marca.
        markerOptions.icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_VIOLET
            )
        )

        // Se añade la marca al mapa.
        map.addMarker(markerOptions)
    }

    private val geocodeListener = @RequiresApi(33) object : Geocoder.GeocodeListener {
        override fun onGeocode(p0: MutableList<Address>) {
            Log.d("GeocodeListener", p0.toString())
        }
    }

    // Método para obtener la dirección de una ubicación.
    private fun getAddress(location: LatLng): String {
        // Se crea un objeto Geocoder para obtener la dirección.
        val geocoder = Geocoder(this)
        // Se pueden obtener más de una dirección de un mismo punto.
        val addresses: MutableList<Address>?
        val address: Address?
        var addressText = ""

        try {
            // Se obtiene la información del punto concreto.
            if (Build.VERSION.SDK_INT >= 33) {
//                geocoder.getFromLocation(
//                    location.latitude,
//                    location.longitude,
//                    1,
//                    geocodeListener
//                )
            } else {
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                // Si se obtiene la dirección, se añade a la variable addressText.
                if (!addresses.isNullOrEmpty()) {
                    // Se coge la primera posición.
                    address = addresses[0]
                    // Se comprueba que la dirección tenga o no más de una línea.
                    if (address.maxAddressLineIndex > 0) {
                        for (i in 0 until address.maxAddressLineIndex) {
                            addressText += if (i == 0) address.getAddressLine(i)
                            else "\n${address.getAddressLine(i)}"
                        }
                    } else { // Acción más habitual.
                        addressText += "${address.thoroughfare}, ${address.subThoroughfare}\n"
                    }
                }
            }
        } catch (e: IOException) {
            e.localizedMessage?.let { Log.e("MapsActivity", it) }
        }

        return addressText
    }
}