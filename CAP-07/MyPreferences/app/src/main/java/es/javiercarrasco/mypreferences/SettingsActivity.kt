package es.javiercarrasco.mypreferences

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mypreferences.databinding.ActivitySettingsBinding

const val EMPTY_VALUE = ""

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se implementa el callback para la pulsación "atrás".
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("onBackPressedCallback", "handleOnBackPressed")
                if (!binding.etName.text!!.isEmpty())
                    SharedApp.preferences.name = binding.etName.text.toString().trim()

                finish()
            }
        })

        // Se comprueba si existen propiedades creadas para cargarlas.
        configView()

        // Botón para eliminar las preferencias.
        binding.btnDeletePrefs.setOnClickListener {
            binding.etName.text = null
            SharedApp.preferences.deletePrefs()
            // onBackPressed()
            onBackPressedDispatcher.onBackPressed()
        }

        // Se controla el Enter del teclado y la pulsación.
        binding.etName.setOnEditorActionListener { textView, actionId, keyEvent ->
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                onBackPressedDispatcher.onBackPressed()
            }

            true
        }
    }

    // Se controla si se pulsa el botón "Atrás" de la barra de app.
    // Se controla como una opción de menú más.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // onBackPressed()
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Método que indica si se pulsa el botón "Atrás" del propio sistema operativo API < 33.
//    override fun onBackPressed() {
//        // Gracias a la clase Preferences, la asignación se encarga de editar y guardar.
//        if (!binding.etName.text!!.isEmpty())
//            SharedApp.preferences.name = binding.etName.text.toString().trim()
//
//        super.onBackPressed()
//    }

    // Muestra el valor de la propiedad en el EditText.
    fun showPrefs() {
        binding.etName.setText(SharedApp.preferences.name)
    }

    // Comprueba si existe la propiedad.
    fun configView() {
        if (isSavedName()) showPrefs()
    }

    fun isSavedName() = SharedApp.preferences.name != EMPTY_VALUE
}