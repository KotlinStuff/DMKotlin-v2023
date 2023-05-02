package es.javiercarrasco.myroom

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import es.javiercarrasco.myroom.adapters.SupersRecyclerAdapter
import es.javiercarrasco.myroom.data.SupersDataSource
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.databinding.ActivityMainBinding
import es.javiercarrasco.myroom.ui.editorial.EditorialActivity
import es.javiercarrasco.myroom.ui.superhero.SuperheroActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SupersRecyclerAdapter

    private val vm: MainViewModel by viewModels {
        val db = (application as MyRoomApplication).supersDatabase
        val supersDataSource = SupersDataSource(db.supersDAO())
        val supersRepository = SupersRepository(supersDataSource)
        MainViewModelFactory(supersRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SupersRecyclerAdapter(
            onSuperHeroClick = {
                SuperheroActivity.navigate(
                    this@MainActivity,
                    it.idSuper
                )
            },
            onSuperHeroLongClick = { vm.onSuperDelete(it) },
            onFabClick = { vm.onFabSuper(it) }
        )

        binding.recycler.adapter = adapter

        // Alternativa para evitar el problema de la des-subscripción.
        lifecycleScope.launch {
            // En este método se indica en que estado comenzará a recolectar (STARTED),
            // y en su opuesto (ON_STOP) se detendrá.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

//    private fun updateRecycler() {
//        CoroutineScope(Dispatchers.Main).launch {
//            withContext(Dispatchers.IO) {
//                db.supersDAO().getAllSuperHerosWithEditorials()
//            }.run {
//                adapter.submitList(this)
//            }
//        }
//    }

    // Gestión del menú principal
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opAddEditorial -> {
                EditorialActivity.navigate(this@MainActivity)
                true
            }

            R.id.opAddSuper -> {
                SuperheroActivity.navigate(this@MainActivity)
                true
            }

            else -> false
        }
    }
}