package es.javiercarrasco.myfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.javiercarrasco.myfragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Controla si está cargado o no FragmentOne.
    var isFragmentOneLoaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se muestra en pantalla el primer Fragment.
        showFragment(FragmentOne())

        // Listener del botón.
        binding.button.setOnClickListener {
            if (isFragmentOneLoaded)
                showFragment(FragmentOne())
            else showFragment(FragmentTwo())
        }
    }

    private fun showFragment(fragmentToLoad: Fragment) {
        // Se establece la transacción de fragments, necesarios para añadir,
        // quitar o reemplazar fragments.
        val transaction = supportFragmentManager.beginTransaction()

        // Indicamos el elemento del layout donde haremos el cambio.
        transaction.replace(R.id.fragmentHolder, fragmentToLoad)

        // Se establece valor a null para indicar que se está interesado en volver a ese
        // fragment al pulsar atrás, o se indicaría el nombre del fragment al que se quiere
        // volver. Se puede utilizar disallowAddToBackStack() para evitar la pila.
        transaction.addToBackStack(null)
        //transaction.disallowAddToBackStack()

        // Se muestra el fragment.
        transaction.commit()
        isFragmentOneLoaded = !isFragmentOneLoaded
    }
}