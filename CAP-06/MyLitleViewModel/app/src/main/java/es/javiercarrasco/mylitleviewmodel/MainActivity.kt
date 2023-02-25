package es.javiercarrasco.mylitleviewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mylitleviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // private lateinit var  mainViewModel: MainViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se establece la asociación del ViewModel.
        // mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Se actualiza la UI.
        binding.tvSalida.text = mainViewModel.textoEtiqueta

        supportActionBar!!.setSubtitle(
            getString(R.string.txt_subtitle) +
                    " - " +
                    this.localClassName
        )

        Log.d("onCreate", "onCreate")

        binding.btnAccion.setOnClickListener {
            if (binding.tvSalida.text.isBlank())
                binding.tvSalida.text = binding.etTexto.text
            else binding.tvSalida.append("\n${binding.etTexto.text}")

            // Se guardan los cambios en el ViewModel.
            mainViewModel.textoEtiqueta = binding.tvSalida.text.toString()
        }

        binding.btnAccion2.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    override fun onDestroy() {
        Log.d("onDestroy", "onDestroy")
        super.onDestroy()
    }

    // Se guarda el estado de la UI.
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.run {
//            putString("TEXTO", binding.tvSalida.text.toString())
//        }
//
//        // Es necesario llamar al super para guardar los datos.
//        super.onSaveInstanceState(outState)
//    }
//
//    // Restaura el estado de la UI.
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        // Es necesaria la llamada al super para restaurar los datos.
//        super.onRestoreInstanceState(savedInstanceState)
//
//        savedInstanceState.run {
//            binding.tvSalida.text = this.getString("TEXTO")
//        }
//    }
}