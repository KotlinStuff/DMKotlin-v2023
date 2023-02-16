package es.javiercarrasco.myexplicitintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myexplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_MESSAGE = "myMessage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (!binding.edTexto.text.isNullOrEmpty()) {
                // Se crea el objeto Intent.
                val myIntent = Intent(this, SecondActivity::class.java).apply {
                    // Se añade la información a pasar por clave-valor.
                    putExtra(EXTRA_MESSAGE, binding.edTexto.text.toString())
                }

                // Se lanza la nueva activity con el Intent.
                startActivity(myIntent)
            } else {
                binding.textInputLayout.error = getString(R.string.txt_error)
            }
        }
    }
}