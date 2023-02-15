package es.javiercarrasco.mysnackbar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.mysnackbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Snackbar simple.
        binding.button1.setOnClickListener {
            showSnackSimple()
        }

        // Snacbar con acción.
        binding.button2.setOnClickListener {
            showSnackAction()
        }
    }

    // Método encargado de mostrar un Snackbar simple.
    private fun showSnackSimple() {
        Snackbar.make(
            binding.root,
            "Mi primer Snackbar!",
            Snackbar.LENGTH_LONG
        ).show()
    }

    // Método encargado de mostrar un Snackbar capaz de recibir feedback.
    private fun showSnackAction() {
        // Se cambia el color de fondo del layout
        binding.root.setBackgroundColor(Color.YELLOW)

        Snackbar.make(
            binding.root,
            "Mi SnackBar con acción!",
            Snackbar.LENGTH_LONG
        ).setAction(
            "Deshacer" // Texto del botón
        ) { // Acciones al pulsar el botón "Deshacer"
            binding.root.setBackgroundColor(Color.WHITE)
        }.show()
    }
}