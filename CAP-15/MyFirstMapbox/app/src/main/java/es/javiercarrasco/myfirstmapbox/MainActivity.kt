package es.javiercarrasco.myfirstmapbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
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
    }
}