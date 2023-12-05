package es.javiercarrasco.myfirstmapbox

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import es.javiercarrasco.myfirstmapbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Carga de un estilo desde al API Estilos de Mapbox.
        mapbox.loadStyle(Style.OUTDOORS) { style ->
            // Agua de color rojo.
            val singleLayer = style.getLayerAs<FillLayer>("water")
            singleLayer!!.fillColor(Color.RED)
        }
        //mapbox.loadStyle("mapbox://styles/mapbox/dark-v11")

        // Ya se puede añadir información o hacer otros ajustes.
        binding.mapView.logo.enabled = true // Desactiva el logo.
        binding.mapView.attribution.enabled = false // Desactiva la atribución.
        // Mantiene la brújula siempre activada.
        binding.mapView.compass.fadeWhenFacingNorth = false
    }
}