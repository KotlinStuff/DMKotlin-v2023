package es.javiercarrasco.mybaseadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.mybaseadapter.adapters.AdapterCursos
import es.javiercarrasco.mybaseadapter.databinding.ActivityMainBinding
import es.javiercarrasco.mybaseadapter.databinding.ItemListaBinding
import es.javiercarrasco.mybaseadapter.model.Curso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var datos = ArrayList<Curso>()
    private lateinit var adaptador: AdapterCursos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datos.add(Curso("PMM", "Multimedia y móviles"))
        datos.add(Curso("AD", "Acceso a datos"))
        datos.add(Curso("DI", "Desarrollo de interfaces"))

        adaptador = AdapterCursos(datos)
        binding.listView.adapter = adaptador
    }

    override fun onStart() {
        super.onStart()

        binding.listView.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Snackbar.make(
                        binding.root,
                        datos[position].nombre,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            }
    }
}