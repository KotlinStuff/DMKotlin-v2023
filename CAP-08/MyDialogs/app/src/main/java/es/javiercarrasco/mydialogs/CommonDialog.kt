package es.javiercarrasco.mydialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CommonDialog(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).run {
                setMessage(message)
                setPositiveButton(android.R.string.ok) { dialog, which ->
                    // Acciones si se pulsa OK.
                    Log.d("DEBUG", "Acciones si acepta.")
                    Toast.makeText(
                        it,
                        "Acciones si acepta",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                setNeutralButton(android.R.string.cancel) { dialog, which ->
                    // Acciones si se pulsa CANCEL.
                    Log.d("DEBUG", "Acciones si cancela.")
                    Toast.makeText(
                        it,
                        "Acciones si cancela",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.create()
        } ?: throw IllegalStateException("La Activity no puede ser nula")
    }
}