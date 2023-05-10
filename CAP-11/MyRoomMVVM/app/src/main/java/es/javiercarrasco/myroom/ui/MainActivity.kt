package es.javiercarrasco.myroom.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myroom.MyRoomApplication
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.adapters.SupersRecyclerAdapter
import es.javiercarrasco.myroom.data.SupersDataSource
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.databinding.ActivityMainBinding
import es.javiercarrasco.myroom.ui.editorial.EditorialActivity
import es.javiercarrasco.myroom.ui.superhero.SuperheroActivity
import kotlinx.coroutines.flow.collect
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
            onSuperHeroLongClick = {
                vm.onSuperDelete(it)
                Snackbar.make(
                    binding.root,
                    getString(R.string.warning_delete, it.superName),
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.warning_undo) { v ->
                    vm.onSuperInsert(it)
                }.show()
            },
            onFabClick = { vm.onFabSuper(it) }
        )

        binding.recycler.adapter = adapter

        // Se evitan problemas de la des-subscripción.
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

    override fun onStart() {
        super.onStart()

        vm.getNumEditorials()
        println("NUM EDs: ${vm.getNumEditorials()}")
    }

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
                println("NUM EDs: ${vm.getNumEditorials()}")
                if (vm.getNumEditorials() > 0)
                    SuperheroActivity.navigate(this@MainActivity)
                true
            }

            else -> false
        }
    }
}