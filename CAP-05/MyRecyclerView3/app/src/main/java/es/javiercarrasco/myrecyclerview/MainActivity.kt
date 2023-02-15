package es.javiercarrasco.myrecyclerview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: RecyclerAdapter
    private var listAnimals: MutableList<MyAnimal> = getAnimals()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        binding.swipeRefresh.setOnRefreshListener {
            // Se limpia la lista.
            listAnimals.clear()

            // Se vuelven a añadir todos los elementos.
            listAnimals.addAll(getAnimals())

            // Se actualiza el adapter.
            myAdapter.notifyDataSetChanged()

            // Se desactiva el refresco.
            binding.swipeRefresh.isRefreshing = false
        }
    }

    // Método encargado de configurar el RV.
    private fun setUpRecyclerView() {
        // Esta opción a TRUE significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación.
        binding.myRVAnimals.setHasFixedSize(true)

        // Se indica el contexto para RV en forma de lista.
        binding.myRVAnimals.layoutManager = LinearLayoutManager(this)

        // Se genera el adapter.
        myAdapter = RecyclerAdapter(listAnimals)

        // Se asigna el adapter al RV.
        binding.myRVAnimals.adapter = myAdapter

        binding.myRVAnimals.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                    Log.d("SCROLL", "Down")
                else if (dy < 0)
                    Log.d("SCROLL", "Up")
            }
        })
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