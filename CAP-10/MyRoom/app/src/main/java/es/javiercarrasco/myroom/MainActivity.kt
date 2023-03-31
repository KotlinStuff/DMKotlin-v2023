package es.javiercarrasco.myroom

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.databinding.ActivityMainBinding
import es.javiercarrasco.myroom.ui.EditorialActivity
import es.javiercarrasco.myroom.ui.ListviewFragment
import es.javiercarrasco.myroom.ui.RecyclerviewFragment
import es.javiercarrasco.myroom.ui.SuperheroActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: SupersDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = (application as MyRoomApplication).supersDatabase

        //.getEditorials()

        with(binding.tabLayout) {
            addTab(newTab().setText(getString(R.string.txt_listview)))
            addTab(newTab().setText(getString(R.string.txt_recyclerview)))
        }

        //showFragment(ListviewFragment(db))
    }

    override fun onStart() {
        super.onStart()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.text.toString()) {
                    getString(R.string.txt_listview) -> {
                        showFragment(ListviewFragment(db))
                    }
                    getString(R.string.txt_recyclerview) -> {
                        showFragment(RecyclerviewFragment(db))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab!!.text.toString()) {
                    getString(R.string.txt_listview) -> {
                        showFragment(ListviewFragment(db))
                    }
                    getString(R.string.txt_recyclerview) -> {
                        showFragment(RecyclerviewFragment(db))
                    }
                }
            }
        })
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(
            binding.fragmentHolder.id,
            fragment
        )

        transaction.disallowAddToBackStack()
        transaction.commit()
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