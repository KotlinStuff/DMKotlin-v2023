package es.javiercarrasco.myimagedownloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.*
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myimagedownloader.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Contendrá las URLs de las imágenes a descargar.
    private val urlImages: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readURLs()
    }

    override fun onStart() {
        super.onStart()
        var tarea: Job? = null

        binding.button.setOnClickListener {
            if (binding.button.text == getString(R.string.btn_imageDownloader)) {
                // Se comprueba el estado de la conexión.
                if (checkConnection()) {
                    // Se comprueba la existencia de ImageViews, si existen se eliminan.
                    if (binding.linearLayout.childCount > 3) {
                        binding.linearLayout.removeViews(
                            3,
                            (binding.linearLayout.childCount) - 3
                        )
                    }
                    tarea = downloadImages()
                } else Snackbar.make(it, "Sin conexión", Snackbar.LENGTH_LONG).show()
            } else {
                tarea?.let {
                    tarea?.cancel()
                    binding.button.text = getString(R.string.btn_imageDownloader)
                    binding.textInfo.text = getString(R.string.txt_descargaCancelada)
                }
            }
        }
    }

    // Método encargado de comprobar la conexión.
    private fun checkConnection(): Boolean {
        var estado = false

        // Se comprueba el estado de la conexión.
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetwork
        val actNetwork: NetworkCapabilities? = cm.getNetworkCapabilities(networkInfo)

        if (actNetwork != null)
            estado = when {
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        return estado
    }

    // Coroutine encargada de lanzar la lectura del fichero RAW.
    private fun readURLs() = CoroutineScope(Dispatchers.Main).launch {
        // Preparación
        binding.progressBarURL.isVisible = true

        withContext(Dispatchers.IO) {
            readFile()
            // Retiene la ejecución para ver la UI.
            delay(2000)
        }

        // Fin de la lectura del fichero
        Log.i("FILE", urlImages.size.toString())
        binding.button.isEnabled = urlImages.size > 0
        binding.progressBarURL.isVisible = false
    }

    // Lee el fichero RAW con las URLs de las imágenes para almacenarlas en la lista urlImages.
    private fun readFile() {
        val entrada = InputStreamReader(resources.openRawResource(R.raw.urlimages))
        lateinit var br: BufferedReader

        try {
            br = BufferedReader(entrada)
            var linea = br.readLine()

            while (!linea.isNullOrEmpty()) {
                urlImages.add(linea)
                Log.i("FILE", linea)
                linea = br.readLine()
            }
        } catch (e: IOException) {
            Log.e("ERROR IO", e.message.toString())
        } finally {
            br.close()
            entrada.close()
        }
    }

    // Coroutine encargada de lanzar la descarga de imágenes.
    private fun downloadImages() = CoroutineScope(Dispatchers.Main).launch {
        val images = ArrayList<Bitmap>()

        binding.button.text = getString(android.R.string.cancel)
        binding.textInfo.text = getString(R.string.txt_descargando)
        binding.progressBar.progress = 0

        urlImages.forEach {
            Log.d("URLs", it)

            // Se descargan las imágenes y se almacenan en un ArrayList<Bitmatp>().
            withContext(Dispatchers.IO) {
                try {
                    val inputStream = URL(it).openStream()
                    images.add(BitmapFactory.decodeStream(inputStream))
                    inputStream.close()
                } catch (e: Exception) {
                    Log.e("DOWNLOAD", e.message.toString())
                }
            }
            binding.progressBar.progress = (images.size * 100) / urlImages.size
        }

        // Fin de la tarea. Se añaden las imágenes a la vista una vez descargadas.
        images.forEach {
            addImage(it)
        }

        binding.button.text = getString(R.string.btn_imageDownloader)
        binding.textInfo.text = getString(R.string.txt_descargaCompleta, images.size)

        vibrate()
        val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.mario)
        mediaPlayer.start()
    }

    // Método encargado de "inflar" los ImageView.
    private fun addImage(image: Bitmap) {
        val img = ImageView(this)

        // Se carga la imagen en el ImageView mediante Glide y se ajusta el tamaño.
        Glide.with(this)
            .load(image)
            .override(binding.linearLayout.width - 100)
            .into(img)
        img.setPadding(0, 0, 0, 10)

        // Se infla el LinearLayout con una imagen nueva.
        binding.linearLayout.addView(img)
    }

    private fun vibrate() {
        // Se instancia el objeto Vibrator según la API utilizada
        val vibrator: Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator = vibratorManager.defaultVibrator
        } else {
            vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator // Deprecated API 31
        }

        // Se comprueba la existencia de vibrador.
        if (!vibrator.hasVibrator())
            Toast.makeText(this, "No tienes vibrador!!", Toast.LENGTH_SHORT).show()
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val vibrationEffect = VibrationEffect.createOneShot(
                    1000,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
                vibrator.vibrate(vibrationEffect)
            } else {
                vibrator.vibrate(1000) // Deprecated API 26
                Toast.makeText(this, "Deprecated version", Toast.LENGTH_SHORT).show()
            }
        }
    }
}