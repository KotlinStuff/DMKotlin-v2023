package es.javiercarrasco.mynavigationdrawer

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.javiercarrasco.mynavigationdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val myBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.myDrawerLayout.isOpen) {
                binding.myDrawerLayout.close()
            } else finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, myBackPressed)

        // Se carga la barra de acción.
        setSupportActionBar(binding.myToolBar)

        // Se añade el botón "hamburgesa" a la toolbar y se vincula con el DrawerLayout.
        val toggle = ActionBarDrawerToggle(
            this,
            binding.myDrawerLayout,
            binding.myToolBar,
            R.string.txt_open,
            R.string.txt_close
        )
        binding.myDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Maneja la pulsación del menú.
        binding.myNavigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true // Deja marcada la opción seleccionada.
            binding.myDrawerLayout.close() // Cierra el menú.

            val transaction = supportFragmentManager.beginTransaction().apply {
                replace(
                    binding.fragmentContainer.id, crearFragment(
                        menuItem.title.toString(),
                        "Fragment de ${menuItem.title}"
                    )
                )
                // Permite la vuelta "atrás".
                // addToBackStack(null) // Al estar sobrecargado el BackPressed no se vuelve atrás en la pila.
            }

            transaction.commit()

            true
        }

        // Se añade el fragment al FrameLayout.
        supportFragmentManager.beginTransaction()
            .add(
                binding.fragmentContainer.id,
                crearFragment(
                    "Inicio",
                    "Fragment de inicio"
                )
            ).commit()
    }

    // Método encargado de crear los fragments.
    private fun crearFragment(titulo: String, subtitulo: String): Fragment {
        val fragment = PageFragment()
        val bundle = Bundle()

        bundle.putString("titulo", titulo)
        bundle.putString("subtitulo", subtitulo)
        fragment.arguments = bundle

        return fragment
    }
}