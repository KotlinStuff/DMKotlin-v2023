package es.javiercarrasco.mymenus4

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import es.javiercarrasco.mymenus4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivMoreOptions.setOnClickListener {
            showPopupMenu(it)
        }
    }

    // Método encargado de "inflar" el PopupMenu.
    private fun showPopupMenu(v: View) {
        PopupMenu(this, v).apply {
            inflate(R.menu.acciones)
            setOnMenuItemClickListener(::managerClick)
        }.show()
    }

    // Manejador de eventos sobre el PopupMenu.
    private fun managerClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_archive -> {
                showToast("Opción ${getString(R.string.menu_op_archive)}")
                true
            }
            R.id.menu_delete -> {
                showToast("Opción ${getString(R.string.menu_op_delete)}")
                true
            }
            R.id.menu_save -> {
                showToast("Opción ${getString(R.string.menu_op_save)}")
                true
            }
            else -> false
        }
    }

    private fun showToast(texto: CharSequence){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
    }
}