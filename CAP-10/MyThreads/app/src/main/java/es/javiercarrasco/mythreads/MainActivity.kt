package es.javiercarrasco.mythreads

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mythreads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            createThread(binding.btn1, 500, 10, binding.tv1, binding.progressBar1)
        }

        binding.btn2.setOnClickListener {
            createThread(binding.btn2, 100, 20, binding.tv2, binding.progressBar2)
        }

        binding.btn3.setOnClickListener {
            createThread(binding.btn3, 200, 30, binding.tv3, binding.progressBar3)
        }

        binding.btn4.setOnClickListener {
            createThread(binding.btn4, 300, 40, binding.tv4, binding.progressBar4)
        }
    }

    // Método encargado de crear hilos.
    private fun createThread(
        btn: Button,
        duration: Long,
        laps: Int,
        display: TextView,
        progress: ProgressBar
    ) {
        display.text = "0"
        display.setBackgroundColor(Color.TRANSPARENT)
        btn.isEnabled = false
        progress.progress = 0

        Thread(Runnable {
            // Se imitará la realización de una tarea.
            var counter = 0
            val sectionBar: Int = 100 / laps

            while (counter < laps) {
                try {
                    counter++
                    Thread.sleep(duration)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                // Permite actuar con elementos de la UI.
                display.post {
                    display.text = counter.toString()
                    progress.progress += sectionBar
                }
            }

            // Acciones que se realizarán al finalizar la tarea.
            runOnUiThread {
                display.text = "FIN"
                display.setBackgroundColor(Color.GREEN)
                btn.isEnabled = true
                progress.progress = 100
                Toast.makeText(this, btn.text, Toast.LENGTH_SHORT).show()
            }
        }).start()
    }
}