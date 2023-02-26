package es.javiercarrasco.mymenus2

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mymenus2.databinding.ActivityMainBinding

/**
 * Created by Javier Carrasco on 16/11/22.
 * App: My Menus 2
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Controla la activación del modo de la ActionBar.
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etiqueta.setOnLongClickListener {
            when (actionMode) {
                null -> {
                    // Se lanza el ActionMode.
                    actionMode = it.startActionMode(actionModeCallback)
                    it.isSelected = true
                    true
                }
                else -> false
            }
        }
    }

    // Modo de acción contextual.
    private val actionModeCallback = object : ActionMode.Callback {
        // Método llamado al selección una opción del menú.
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?)
                : Boolean {
            return when (item!!.itemId) {
                R.id.option01 -> {
                    Toast.makeText(
                        applicationContext,
                        R.string.menu_op01,
                        Toast.LENGTH_SHORT
                    ).show()
                    // Se deshabilita la etiqueta.
                    binding.etiqueta.visibility = View.GONE

                    // Se cierra el menú.
                    actionMode!!.finish()
                    return true
                }
                R.id.option02 -> {
                    Toast.makeText(
                        applicationContext,
                        R.string.menu_op02,
                        Toast.LENGTH_SHORT
                    ).show()
                    return true
                }
                else -> false
            }
        }

        // Llamado al crear el modo acción a través de startActionMode().
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        // Se llama cada vez que el modo acción se muestra, después de onCreateActionMode().
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        // Se llama cuando el usuario sale del modo de acción.
        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }
    }
}