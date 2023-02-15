package es.javiercarrasco.mygridview2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import es.javiercarrasco.mygridview2.databinding.ActivityMainBinding
import es.javiercarrasco.mygridview2.databinding.GridviewItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var adapter: ItemAdapter? = null
    var itemsList = ArrayList<MyItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se crea la fuente de datos con imágenes de muestra.
        itemsList.add(
            MyItems(
                "Estrella", android.R.drawable.btn_star
            )
        )
        itemsList.add(
            MyItems(
                "Botón Check", android.R.drawable.checkbox_off_background
            )
        )
        itemsList.add(
            MyItems(
                "Lupa", android.R.drawable.ic_menu_search
            )
        )
        itemsList.add(
            MyItems(
                "Candado", android.R.drawable.ic_lock_lock
            )
        )
        itemsList.add(
            MyItems(
                "Launcher Back", R.drawable.ic_launcher_background
            )
        )
        itemsList.add(
            MyItems(
                "Launcher Icon", R.drawable.ic_launcher_foreground
            )
        )
        itemsList.add(
            MyItems(
                "Launcher", R.mipmap.ic_launcher
            )
        )

        // Se generamos el adaptador.
        adapter = ItemAdapter(this, itemsList)

        // Asignamos el adapter
        binding.myGridView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        binding.myGridView.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                val bindingItem = GridviewItemBinding.bind(p1!!)
                Toast.makeText(
                    applicationContext,
                    "Pulsado ${bindingItem.tvName.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}