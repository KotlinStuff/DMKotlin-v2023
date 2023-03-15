package es.javiercarrasco.myfragments3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myfragments3.databinding.ActivityMainBinding

internal const val ARG_NUMFRAG = "numFrag"
internal const val ARG_COLORBACK = "colorBack"

class MainActivity : AppCompatActivity(), NewFragment.RequestData {
    private lateinit var binding: ActivityMainBinding
    private var numfrag = 0

    override fun resultData(dato: String) {
        binding.tvInfoRecibida.text = dato
    }

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

        transaction.replace(binding.fragmentHolder.id, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}