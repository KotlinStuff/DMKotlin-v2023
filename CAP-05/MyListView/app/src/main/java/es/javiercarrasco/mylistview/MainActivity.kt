package es.javiercarrasco.mylistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import es.javiercarrasco.mylistview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Fuente de datos para el ListView.
    private val nombres = arrayOf(
        "Javier", "Nacho", "Patricia", "Miguel", "Susana", "Rosa", "Juan",
        "Pedro", "Asunción", "Antonio", "Lorena", "Verónica", "Paola",
        "Esteban", "Andrea", "María"
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se crea el Adapter uniendo la vista y los datos.
        // val adapter = ArrayAdapter(this, R.layout.listview_item, nombres)

        // Se establece la fuente de datos.
        val nombres2 = resources.getStringArray(R.array.array_nombres)
        // Se crea el Adapter uniendo la vista y los datos.
        val adapter = ArrayAdapter(this, R.layout.listview_item, nombres2)

        // Se asigna el Adapter al Listview.
        binding.myListView.adapter = adapter

        // Se utiliza un AdapterView para conocer el elemento pulsado.
        binding.myListView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(
                    applicationContext,
                    "${binding.myListView.getItemAtPosition(p2)}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}