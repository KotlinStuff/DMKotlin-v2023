package es.javiercarrasco.myautocomplete

import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myautocomplete.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAutoComplete()
    }

    private fun setUpAutoComplete() {
        // Se crea un adapter de tipo Array.
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_nombres,
            android.R.layout.simple_list_item_1
        )
        // Se asigna el adapter al AutoComplete.
        binding.autoCompleteTextView.setAdapter(adapter)

        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, binding.autoCompleteTextView.text, Toast.LENGTH_SHORT).show()
        }
    }
}