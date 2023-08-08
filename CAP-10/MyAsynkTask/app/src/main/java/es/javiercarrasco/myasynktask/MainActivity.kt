package es.javiercarrasco.myasynktask

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myasynktask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se deshabilitan los botones cancelar hasta que se activen las tareas.
        binding.btn1cancel.isEnabled = false
        binding.btn2cancel.isEnabled = false

        // Variables para crear las tareas asíncronas.
        lateinit var myAsyncTask1: MyAsyncTask
        lateinit var myAsyncTask2: MyAsyncTask

        binding.btn1.setOnClickListener {
            myAsyncTask1 = MyAsyncTask(binding.progressBar1, binding.btn1, binding.btn1cancel)
            // Se lanza la tarea.
            myAsyncTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 100, 20)
        }

        binding.btn1cancel.setOnClickListener {
            myAsyncTask1.cancel(true)
        }

        binding.btn2.setOnClickListener {
            myAsyncTask2 = MyAsyncTask(binding.progressBar2, binding.btn2, binding.btn2cancel)
            myAsyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 200, 15)
        }

        binding.btn2cancel.setOnClickListener {
            myAsyncTask2.cancel(true)
        }
    }
}