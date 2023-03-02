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
    }

    // Objeto para recoger la respuesta de la activity.
    var resultadoActivity =
        registerForActivityResult(StartActivityForResult()) { result ->
            // Se recupera la información adicional.
            val data: Intent? = result.data

            if (result.resultCode == Activity.RESULT_OK) {
                val valoracion = data?.getFloatExtra("RATING", 0.00F)
                binding.tvResult.text = getString(R.string.txt_Accepted, valoracion.toString())
            }
            if (result.resultCode == Activity.RESULT_CANCELED)
                binding.tvResult.text = getString(R.string.txt_noAccepted)

            binding.tvResult.visibility = View.VISIBLE

            Log.d(TAG_APP, result.resultCode.toString())
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
        Log.d(TAG_APP, "askConditions")

        // Se vuelve a ocultar el TV que mostrará el resultado.
        binding.tvResult.visibility = View.INVISIBLE

        resultadoActivity.launch(
            SecondActivity.navigateToSecondActivity(this, binding.edTexto.text.toString())
        )
    }
}