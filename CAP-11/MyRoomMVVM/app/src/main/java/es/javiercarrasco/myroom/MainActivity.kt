package es.javiercarrasco.myroom

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myroom.adapters.SupersRecyclerAdapter
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.databinding.ActivityMainBinding
import es.javiercarrasco.myroom.ui.editorial.EditorialActivity
import es.javiercarrasco.myroom.ui.superhero.SuperheroActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: SupersDatabase
    private lateinit var adapter: SupersRecyclerAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        db = (application as MyRoomApplication).supersDatabase
//
//        adapter = SupersRecyclerAdapter(
//            onSuperHeroClick = {
//                SuperheroActivity.navigate(
//                    this@MainActivity,
//                    it.idSuper
//                )
//            },
//            onSuperHeroLongClick = {
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.supersDAO().deleteSuperHero(it)
//                    adapter.submitList(db.supersDAO().getAllSuperHerosWithEditorials())
//                }
//            },
//            onFabClick = {
//                CoroutineScope(Dispatchers.IO).launch {
//                    val updated = it.copy(
//                        favorite = if (it.favorite == 0) 1 else 0
//                    )
//                    db.supersDAO().insertSuperHero(updated)
//                    adapter.submitList(db.supersDAO().getAllSuperHerosWithEditorials())
//                }
//            }
//        )
//
//        binding.recycler.adapter = adapter
//
//        updateRecycler()
//    }
//
//    private fun updateRecycler() {
//        CoroutineScope(Dispatchers.Main).launch {
//            withContext(Dispatchers.IO) {
//                db.supersDAO().getAllSuperHerosWithEditorials()
//            }.run {
//                adapter.submitList(this)
//            }
//        }
//    }
    override fun onResume() {
        super.onResume()
        //updateRecycler()
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
                SuperheroActivity.navigate(this@MainActivity)
                true
            }
            else -> false
        }
    }
}