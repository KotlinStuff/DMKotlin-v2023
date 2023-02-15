package es.javiercarrasco.myapplication2

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myapplication2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // binding.imageView.setImageResource(R.mipmap.ic_launcher_round)

            // Se muestra el Snackbar.
            showSnackSimple()

            // Se carga la imagen en el ImageView.
            Glide.with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/c/cb/Kotlin_vs_java.jpg") // Recurso.
                .override(300, 300) // Ajusta el tamaño.
                .centerCrop() // Centra la imagen.
                .into(binding.imageView) // Contenedor.

            binding.myInputLabel.error = "Error!!"
            //binding.myInput.error = "Error!!!"
        }
    }

    // Método encargado de mostrar un Snackbar sencillo.
    private fun showSnackSimple() {
        Snackbar.make(
            binding.root,
            "Mi Snackbar en acción!!\nPuntuación: ${binding.ratingBar.rating}",
            Snackbar.LENGTH_LONG
        ).show()
    }
}