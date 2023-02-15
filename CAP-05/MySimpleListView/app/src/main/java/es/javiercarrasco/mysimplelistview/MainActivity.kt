package es.javiercarrasco.mysimplelistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import es.javiercarrasco.mysimplelistview.databinding.ActivityMainBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val datos = arrayOf(
        "uno", "dos", "tres", "cuatro"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // lista pre-diseñada
            datos
        )

        binding.listView.adapter = adaptador

        binding.listView.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Pulsado ${datos[position]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}