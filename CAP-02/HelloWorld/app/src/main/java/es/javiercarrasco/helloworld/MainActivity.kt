package es.javiercarrasco.helloworld

/**
 * @author Javier Carrasco
 * Proyecto HelloWorld!!. Captítulo 1.
 */

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Variables privadas para obtener las referencias a los
    // componentes de la vista.
    private lateinit var tvSaludo: TextView
    private lateinit var etSaludo: EditText
    private lateinit var btnSaludar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("onCreate", "Activity creada!!")

        // Se inyectan los componentes de la vista.
        tvSaludo = findViewById(R.id.tv_saludo)
        etSaludo = findViewById(R.id.et_saludo)
        btnSaludar = findViewById(R.id.btn_saludar)

        // Listener del botón para capturar la pulsación.
        btnSaludar.setOnClickListener { btn ->
            if (etSaludo.text.isBlank()) {
                etSaludo.error = resources.getString(R.string.texto_error)
                Toast.makeText(this, R.string.texto_error, Toast.LENGTH_SHORT).show()
                btn.setBackgroundColor(Color.RED)
            } else {
                etSaludo.error = null
                tvSaludo.text = resources.getString(R.string.texto_saludo, etSaludo.text.trim())
                btn.setBackgroundColor(this.getColor(R.color.purple_500))
            }
        }
    }

    // Sobrecarga de los métodos que intevienen en el
    // ciclo de vida de una activity.
    override fun onStart() {
        super.onStart()
        Log.d("onStart", "Método onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "Método onResume()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart", "Método onRestart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", "Método onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop", "Método onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Método onDestroy()")
    }

}