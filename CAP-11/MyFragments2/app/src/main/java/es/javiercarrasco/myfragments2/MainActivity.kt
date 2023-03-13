package es.javiercarrasco.myfragments2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myfragments2.databinding.ActivityMainBinding

internal const val ARG_NUMFRAG = "numFrag"
internal const val ARG_COLORBACK = "colorBack"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var numfrag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se muestra el primer fragment.
        showFragment()
        binding.button.setOnClickListener {
            showFragment()
        }
    }

    private fun showFragment() {
        val transaction = supportFragmentManager.beginTransaction()

        // Declaración del Fragment mediante newInstance.
        val fragment = NewFragment.newInstance(
            ++numfrag,
            (if ((numfrag % 2) == 0) getColor(R.color.colorPar) else getColor(R.color.colorImpar))
        )

        // Declaración básica del Fragment.
//        val fragment = NewFragment()
        // Se crea la variable Bundle para "empaquetar" los datos a pasar.
//        val bundle = Bundle()
//        bundle.putInt(ARG_NUMFRAG, ++numfrag)
//        bundle.putInt(
//            ARG_COLORBACK,
//            (if ((numfrag % 2) == 0)
//                getColor(R.color.colorPar)
//            else getColor(R.color.colorImpar))
//        )
//        fragment.arguments = bundle

        transaction.replace(binding.fragmentHolder.id, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}