package es.javiercarrasco.myasynktask

import android.os.AsyncTask
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
class MyAsyncTask(progressBar: ProgressBar, btnStart: Button, btnCancel: Button) :
    AsyncTask<Int, Int, Int>() {

    // Se evita las pérdidas de memoria debido al contexto de las vistas.
    private val progressBar: WeakReference<ProgressBar> = WeakReference(progressBar)
    private val btnStart: WeakReference<Button> = WeakReference(btnStart)
    private val btnCancel: WeakReference<Button> = WeakReference(btnCancel)

    // Antes de iniciar la tarea, se utiliza para inicializar o configurar la tarea.
    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        Log.d(btnStart.get()!!.text.toString(), "${btnStart.get()!!.text}, iniciada!!")
        progressBar.get()!!.progress = 0
        btnStart.get()!!.isEnabled = false
        btnCancel.get()!!.isEnabled = true
    }

    // Este método realiza la tarea a ejecutar, su implementación es obligatoria.
    // vararg -> array con los parámetros indicados.
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg p0: Int?): Int {
        if (p0.size == 2) {
            var contador = 0
            while (contador < p0[0]!!) {
                try {
                    contador++
                    Thread.sleep(p0[1]!!.toLong())
                } catch (e: Exception) {
                    Log.println(Log.WARN, "doInBackground", e.message.toString())
                }
                // Se comprueba si la tarea ha sido cancelada.
                if (!isCancelled)
                    publishProgress((((contador + 1) * 100 / p0[0]!!).toFloat()).toInt())
                else break
            }
            return 1
        } else return -1
    }

    // Método que se ejecutará al cancelarse la tarea.
    @Deprecated("Deprecated in Java")
    override fun onCancelled(result: Int?) {
        val ctxt = progressBar.get()!!.context
        progressBar.get()!!.progress = 0
        Log.d(btnStart.get()!!.text.toString(), "${btnStart.get()!!.text}, cancelada!!")
        Toast.makeText(
            ctxt, "${btnStart.get()!!.text}, cancelada!!", Toast.LENGTH_SHORT
        ).show()

        btnStart.get()!!.isEnabled = true
        btnCancel.get()!!.isEnabled = false
    }

    // Permite mostrar información al usuario, se ejecuta cuando se utiliza
    // el método publishProgress() desde el método doInBackground().
    @Deprecated("Deprecated in Java")
    override fun onProgressUpdate(vararg values: Int?) {
        progressBar.get()!!.progress = values[0]!!
    }

    // Se ejecuta al finalizar el método doInBackground() y se le pasa el
    // resultado obtenido.
    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Int?) {
        if (result == 1) {
            val ctxt = progressBar.get()!!.context
            progressBar.get()!!.progress = 100
            Toast.makeText(ctxt, btnStart.get()!!.text, Toast.LENGTH_SHORT).show()
        }

        btnStart.get()!!.isEnabled = true
        btnCancel.get()!!.isEnabled = false
    }
}