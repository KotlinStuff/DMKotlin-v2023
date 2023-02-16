package es.javiercarrasco.myapplication

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun actionButtons(name: String) {
        with(binding) {
            when (name) {
                button.javaClass.name -> {
                    checkBox.isChecked = true
                    radioButton1.isChecked = true
                    toggleButton.isChecked = true
                    switch1.isChecked = true
                }
                else -> {
                    if (checkBox.isChecked)
                        checkBox.isChecked = false

                    radioButton2.isChecked = true
                    toggleButton.isChecked = false
                    switch1.isChecked = false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // TO-DO
            Log.i("DISPLAY", resources.configuration.orientation.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Se sustituye la referencia al recurso R.layout.activity_main
        // por el binding.
        setContentView(binding.root)


//        binding.button.setOnClickListener {
//            actionButtons(binding.button.javaClass.name)
//
//            Toast.makeText(
//                this,
//                "Texto de la etiqueta: ${binding.textView.text}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        binding.button.setOnClickListener {
            var message: String = ""

            if (!binding.editText.text.isEmpty()) {
                binding.textView.text = binding.editText.text
                message += "Texto de EditText: ${binding.editText.text}\n"
            }
            message += "Texto de la etiqueta: ${binding.textView.text}"
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.imageButton.setOnClickListener {
            actionButtons(binding.imageButton.javaClass.name)
        }

        binding.btnLandscape?.let {
            it.setOnClickListener {
                Toast.makeText(this, "Botón de la vista horizontal", Toast.LENGTH_SHORT).show()
            }
        }

    }
}