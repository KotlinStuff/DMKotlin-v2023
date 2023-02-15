package es.javiercarrasco.myspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import es.javiercarrasco.myspinner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se crea el Adapter, uniendo la fuente de datos con una vista
        // por defecto para el spinner de Android.
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_nombres,
            android.R.layout.simple_spinner_item
        )

        // Se especifica el diseño que debe utilizarse para mostrar la lista.
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        // Se carga el Adapter en el Spinner.
        binding.mySpinner.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        binding.mySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 > 0) {
                        Toast.makeText(
                            applicationContext,
                            "${binding.mySpinner.getItemAtPosition(p2)}!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    Log.d(
                        "MySpinner",
                        "${binding.mySpinner.getItemAtPosition(p2)}!"
                    )
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // No se hace nada.
                }
            }
    }
}