package es.javiercarrasco.myfirstmapbox

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import es.javiercarrasco.myfirstmapbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionsManager: PermissionsManager

    private var permissionsListener: PermissionsListener = object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            Toast.makeText(
                this@MainActivity,
                "Permissions: $permissionsToExplain",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
                Toast.makeText(
                    this@MainActivity, "Permissions granted", Toast.LENGTH_SHORT
                ).show()
                // activateLocation()
            } else Toast.makeText(
                this@MainActivity,
                "Permissions not granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onMapReady()

        // Gestión del permiso de localización.
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            Log.d("Mapbox-Permission", "Location permissions granted")
            // activateLocation()
        } else {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this)
        }
    }

    private fun onMapReady() {
        // Create a MapboxMap
        val mapbox = binding.mapView.mapboxMap

        mapbox.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-0.5295719, 38.4044311))
                .pitch(0.0) // inclinación, 0.0 es vertical
                .zoom(12.0)
                .bearing(0.0) // rotación, 0.0 es norte
                .build()
        )

        // Ya se puede añadir información o hacer otros ajustes.
        binding.mapView.logo.enabled = true // Desactiva el logo.
        binding.mapView.attribution.enabled = false // Desactiva la atribución.
        binding.mapView.compass.fadeWhenFacingNorth = false // Brújula siempre activa.

        Log.d("Mapbox", "Map is ready")
    }
}