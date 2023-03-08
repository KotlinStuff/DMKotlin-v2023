package es.javiercarrasco.mycoroutines

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mycoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Las corrutinas devuelven un objeto Job al crearse.
        var task1: Job? = null
        var task2: Job? = null

        binding.btn1.setOnClickListener {
            task1 = makeTask(10, binding.btn1, binding.btn1cancel, binding.progressBar1)
        }

        binding.btn2.setOnClickListener {
            task2 = makeTask(15, binding.btn2, binding.btn2cancel, binding.progressBar2)
        }

        binding.btn1cancel.setOnClickListener {
            // LET ejecutará el contenido si task1 existe y/o no es nulo.
            task1.let {
                task1?.cancel().apply {
                    Toast.makeText(
                        this@MainActivity,
                        "${getString(R.string.txt_btn_1)} cancelada!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.progressBar1.progress = 0
                binding.btn1cancel.isEnabled = false
                binding.btn1.isEnabled = true
            }
        }

        binding.btn2cancel.setOnClickListener {
            // LET ejecutará el contenido si task2 existe y/o no es nulo.
            task2.let {
                task2?.cancel().apply {
                    Toast.makeText(
                        this@MainActivity,
                        "${getString(R.string.txt_btn_2)} cancelada!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.progressBar2.progress = 0
                binding.btn2cancel.isEnabled = false
                binding.btn2.isEnabled = true
            }
        }
    }

    // Función de suspensión que detiene la ejecución de la corrutina hasta que finaliza.
    suspend fun task(duracion: Long): Boolean {
        Log.d("SUSPEND FUN", "¡Simulando una tarea!")
        delay(duracion)
        return true
    }

    // Método encargado de crear una corrutina simulando una tarea.
    private fun makeTask(
        duracion: Int, btnStart: Button, btnCancel: Button, progressBar: ProgressBar
    ): Job = CoroutineScope(Dispatchers.Main).launch {
        // Preparación de la corrutina.
        btnStart.isEnabled = false
        btnCancel.isEnabled = true
        progressBar.progress = 0

        withContext(Dispatchers.IO) { // Tarea principal.
            var contador = 0
            while (contador < duracion) {
                if (task((duracion * 50).toLong())) {
                    contador++
                    progressBar.progress = (contador * 100) / duracion
                }
            }
        }

        // Finaliza la corrutina.
        btnStart.isEnabled = true
        btnCancel.isEnabled = false
        progressBar.progress = 0
        Toast.makeText(
            this@MainActivity, "${btnStart.text} finalizada!!", Toast.LENGTH_SHORT
        ).show()
    }
}