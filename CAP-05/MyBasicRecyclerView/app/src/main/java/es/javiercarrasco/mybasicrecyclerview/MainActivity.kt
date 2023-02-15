package es.javiercarrasco.mybasicrecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.mybasicrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val datos = mutableListOf(
        "Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete",
        "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce",
        "Quince", "Dieciséis", "Diecisite", "Dieciocho", "Diecinueve",
        "Veinte"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter =
            object : RecyclerView.Adapter<ViewHolder>() {

                // Devuelve el número de elementos.
                override fun getItemCount(): Int {
                    return datos.size
                }

                // Se encarga de "inflar" la vista.
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    return ViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(android.R.layout.simple_list_item_1, parent, false)
                    )
                }

                // Se une la vista con el dato.
                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    holder.textField.text = datos[position]

                    // Evento onClick sobre la etiqueta.
//                    holder.textField.setOnClickListener {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Pulsado '${datos[position]}'",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
                }
            }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textField: TextView = view.findViewById(android.R.id.text1)

        init {
            // Evento onClick sobre la vista completa.
            itemView.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Pulsado '${textField.text}'",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}