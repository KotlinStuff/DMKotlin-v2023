package es.javiercarrasco.myrecyclerview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: RecyclerAdapter

    companion object {
        private var listAnimals: MutableList<MyAnimal> = MainActivity().getAnimals()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    // Método encargado de configurar el RV.
    private fun setUpRecyclerView() {
        // Esta opción a TRUE significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación.
        binding.myRVAnimals.setHasFixedSize(true)

        // Se indica el contexto para RV en forma de lista.
        binding.myRVAnimals.layoutManager = LinearLayoutManager(this)

        // Se genera el adapter.
        myAdapter = RecyclerAdapter(listAnimals,
            onAnimalLongClick = { animal, pos ->
                Snackbar.make(
                    binding.root,
                    "¿Confirmas el borrado?",
                    Snackbar.LENGTH_LONG
                ).setAction("Sí") {
                    listAnimals.remove(animal)
                    myAdapter.notifyItemRemoved(pos)
                }.show()
            })

        // Se asigna el adapter al RV.
        binding.myRVAnimals.adapter = myAdapter
    }

    // Método encargado de generar la fuente de datos.
    private fun getAnimals(): MutableList<MyAnimal> {
        val animals: MutableList<MyAnimal> = arrayListOf()

        animals.add(MyAnimal("Cisne", "Cygnus olor", R.drawable.cisne))
        animals.add(MyAnimal("Erizo", "Erinaceinae", R.drawable.erizo))
        animals.add(MyAnimal("Gato", "Felis catus", R.drawable.gato))
        animals.add(MyAnimal("Gorrión", "Passer domesticus", R.drawable.gorrion))
        animals.add(MyAnimal("Mapache", "Procyon", R.drawable.mapache))
        animals.add(MyAnimal("Oveja", "Ovis aries", R.drawable.oveja))
        animals.add(MyAnimal("Perro", "Canis lupus familiaris", R.drawable.perro))
        animals.add(MyAnimal("Tigre", "Panthera tigris", R.drawable.tigre))
        animals.add(MyAnimal("Zorro", "Vulpes vulpes", R.drawable.zorro))

        return animals
    }
}