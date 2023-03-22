package es.javiercarrasco.mysqlite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mysqlite.databinding.ActivityMainBinding
import es.javiercarrasco.mysqlite.ui.EditorialActivity
import es.javiercarrasco.mysqlite.ui.SuperheroActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MySQLiteApplication).supersDBHelper.getEditorials()
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
            else -> {
                false
            }
        }
    }
}