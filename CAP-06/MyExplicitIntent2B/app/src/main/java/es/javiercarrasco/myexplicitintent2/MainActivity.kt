package es.javiercarrasco.myexplicitintent2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG_APP = "myExplicitIntent2"
        const val EXTRA_NAME = "userNAME"
    }

    // Objeto para recoger la respuesta de la activity.
    var resultadoActivity =
        registerForActivityResult(StartActivityForResult()) { result ->

            // Si fuese necesario recuperar información adicional
            // se obtendría mediante esta línea.
            val data: Intent? = result.data

            if (result.resultCode == Activity.RESULT_OK)
                binding.tvResult.text = "Condiciones aceptadas."

            if (result.resultCode == Activity.RESULT_CANCELED)
                binding.tvResult.text = "Se canceló el contrato!"

            binding.tvResult.visibility = View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se oculta el TextView que mostrará el resultado.
        binding.tvResult.visibility = View.INVISIBLE

        binding.button.setOnClickListener {
            if (!binding.edTexto.text.isNullOrEmpty())
                askConditions()
            else binding.textInputLayout.error = getString(R.string.txt_error)
        }
    }

    private fun askConditions() {
        Log.d(TAG_APP, "askConditions()")

        // Se vuelve a ocultar el TV que mostrará el resultado.
        binding.tvResult.visibility = View.INVISIBLE
        // Se crea un objeto de tipo Intent.
        val myIntent = Intent(this, SecondActivity::class.java).apply {
            // Se añade la información a pasar por clave-valor.
            putExtra(EXTRA_NAME, binding.edTexto.text.toString())
        }
        // Se lanza la activity.
        resultadoActivity.launch(myIntent)

    }
}