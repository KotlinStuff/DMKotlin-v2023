package es.javiercarrasco.myfirstmapbox

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.mapbox.maps.plugin.gestures.addOnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import es.javiercarrasco.myfirstmapbox.databinding.ActivityMainBinding
import es.javiercarrasco.myfirstmapbox.databinding.AnnotationLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionsManager: PermissionsManager

    // Listener para pasar la ubicación del usuario a la cámara.
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        Log.d("Mapbox-Location", "Location: ${it.latitude()}, ${it.longitude()}")
        // Se actualiza la cámara con la ubicación del usuario.
        binding.mapView.mapboxMap.setCamera(CameraOptions.Builder().center(it).build())

        binding.mapView.gestures.focalPoint = binding.mapView
            .mapboxMap.pixelForCoordinate(it)
    }

    // Listener para activar la rotación de la cámara con el movimiento del usuario.
    private val onIndicationPositionChangedListener = OnIndicatorBearingChangedListener {
        Log.d("Mapbox-Location", "Bearing: $it")
        // Se actualiza la cámara con la ubicación del usuario.
        binding.mapView.mapboxMap.setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private fun activateLocation() {
        // Activación y personalización del puck.
        binding.mapView.location.updateSettings {
            enabled = true
            pulsingEnabled = true
            pulsingColor = Color.RED
            pulsingMaxRadius = 60f
        }

        // Se pasa la ubicación del usuario a la cámara activando un tracker.
        binding.mapView.location.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )

        // Listener para activar la rotación de la cámara con el movimiento del usuario.
        binding.mapView.location.addOnIndicatorBearingChangedListener(
            onIndicationPositionChangedListener
        )
    }

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
                activateLocation()
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
            activateLocation()
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

        // Captura de eventos en el mapa.
        mapbox.addOnMapClickListener {
            // val coordenadas = mapbox.pixelForCoordinate(it)
            val coordenadas = binding.mapView.mapboxMap.project(it, 20.0)
            Toast.makeText(
                this@MainActivity,
                "X: ${coordenadas.x}\nY: ${coordenadas.y}",
                Toast.LENGTH_SHORT
            ).show()

            true
        }


        mapbox.addOnMapLongClickListener { point ->
            Toast.makeText(
                this@MainActivity,
                "Lat: ${point.latitude()}\nLon: ${point.longitude()}",
                Toast.LENGTH_SHORT
            ).show()

            // Se elimina el tracking de la cámara sobre la posición del usuario para evitar
            // que vuelva tras pulsar otro punto.
            binding.mapView.location.removeOnIndicatorPositionChangedListener(
                onIndicatorPositionChangedListener
            )

            mapbox.flyTo(moveCam(point), ANIM_CAM)

            true
        }

        // Detecta el movimiento de la cámara.
        mapbox.subscribeCameraChanged(cameraChangedCallback)

        // Click sobre la brújula.
        binding.mapView.compass.addCompassClickListener {
            activateLocation()
            mapbox.flyTo(RESET_CAM, ANIM_CAM)
        }

        // Detección movimiento del mapa por el usuario.
        mapbox.addOnMoveListener(object : OnMoveListener {
            override fun onMove(detector: MoveGestureDetector): Boolean {
                Log.d("onMove", "Desplazándose.")
                return true
            }

            override fun onMoveBegin(detector: MoveGestureDetector) {
                Log.d("onMoveBegin", "Inicio del desplazamiento.")
            }

            override fun onMoveEnd(detector: MoveGestureDetector) {
                Log.d("onMoveEnd", "Fin del desplazamiento.")
            }
        })

        addAnnotation()
    }

    private val RESET_CAM = cameraOptions {
        zoom(9.0)
        pitch(0.0) // Inclinación.
        bearing(0.0) // Rotación.
    }

    private val cameraChangedCallback = CameraChangedCallback {
        Log.d("Mapbox", "Camera changed: $it")
    }

    private fun moveCam(punto: Point) = cameraOptions {
        zoom(12.0)
        pitch(60.0) // Inclinación.
        bearing(45.0) // Rotación.
        center(punto)
    }

    private val ANIM_CAM = MapAnimationOptions.mapAnimationOptions { duration(2000) }

    override fun onStop() {
        super.onStop()
        binding.mapView.location.removeOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )

        binding.mapView.location.removeOnIndicatorBearingChangedListener(
            onIndicationPositionChangedListener
        )
    }

    private fun addAnnotation() {
        // Se crea una instancia de la API Annotation y se obtiene PointAnnotationManager.
        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        val point = Point.fromLngLat(-0.5292, 38.4044)

        // Se configuran las opciones de la anotación.
        val pointAnnotationOptions = PointAnnotationOptions()
            // Se indica el punto de la anotación.
            .withPoint(point)
            // Se especifica la imagen asociada a la anotación.
            .withIconImage(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.red_marker
                )
            )
            .withIconAnchor(IconAnchor.BOTTOM)

        // Se ajusta el tamaño de la marca.
        pointAnnotationOptions.iconSize = 0.5

        // Se añade el resultado al mapa.
        val pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions)

        // Se prepara el título para la anotación.
        val viewAnnotationManager = binding.mapView.viewAnnotationManager
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            resId = R.layout.annotation_layout,
            options = viewAnnotationOptions {
                annotationAnchor {
                    geometry(point)
                    anchor(ViewAnnotationAnchor.BOTTOM)
                    // Corrección de la posición de la anotación.
                    offsetY(((pointAnnotation.iconImageBitmap?.height!! * pointAnnotation.iconSize!!) + 10))
                }
                //associatedFeatureId(pointAnnotation.featureIdentifier)
            }
        )
        AnnotationLayoutBinding.bind(viewAnnotation).textViewTitulo.text = "IES San Vicente"
    }
}