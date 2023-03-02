package es.javiercarrasco.mydialogs

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.javiercarrasco.mydialogs.databinding.ActivityMainBinding
import es.javiercarrasco.mydialogs.databinding.DialogLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnAlertDialog.setOnClickListener {
                myAlertDialog(getText(R.string.my_first_dialog))
            }

            btnAlertDialogList.setOnClickListener {
                myAlertDialogList(resources.getStringArray(R.array.array_nombres))
            }

            btnAlertDialogSinglePersistentList.setOnClickListener {
                myAlertDialogSinglePersistentList(resources.getStringArray(R.array.array_nombres))
            }

            btnAlertDialogMultiPersistentList.setOnClickListener {
                myAlertDialogMultiPersistentList(resources.getStringArray(R.array.array_nombres))
            }

            btnCustomAlertDialog.setOnClickListener {
                myCustomAlertDialog()
            }
        }
    }

    private fun myCustomAlertDialog() {
        // Se infla el layout personalizado del diálogo.
        val bindingCustom = DialogLayoutBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this).apply {
            setView(bindingCustom.root)

//            setPositiveButton(android.R.string.ok) { _, _ ->
//                Toast.makeText(
//                    context,
//                    "User: ${bindingCustom.etUsername.text}\n" +
//                            "Pass: ${bindingCustom.etPassword.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            setPositiveButton(android.R.string.ok, null)

            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                Toast.makeText(context, android.R.string.cancel, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (bindingCustom.etUsername.text.isNullOrBlank()) {
                    bindingCustom.textInputUsername.error = getString(R.string.warning_username)
                } else {
                    bindingCustom.textInputUsername.error = ""
                    Toast.makeText(
                        this@MainActivity,
                        "User: ${bindingCustom.etUsername.text}\n" +
                                "Pass: ${bindingCustom.etPassword.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    private fun myAlertDialogMultiPersistentList(names: Array<String>) {
        MaterialAlertDialogBuilder(this).apply {
            val selectedItems = ArrayList<Int>()

            setTitle("My AlertDialog con lista multiple")

            setMultiChoiceItems(R.array.array_nombres, null) { _, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(which)
                    Log.d("DEBUG", "Checked: " + names[which])
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(which)
                    Log.d("DEBUG", "UnChecked: " + names[which])
                }
            }

            setPositiveButton(android.R.string.ok) { _, _ ->
                var textToShow = "Checked: "
                if (selectedItems.size > 0) {
                    for (item in selectedItems) {
                        textToShow = textToShow + names[item] + " "
                    }
                } else textToShow = "No items checked!"
                Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show()
            }

            setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(context, android.R.string.cancel, Toast.LENGTH_SHORT).show()
            }
        }.show()
    }

    private fun myAlertDialogSinglePersistentList(names: Array<String>) {
        AlertDialog.Builder(this).apply {
            var selectedPosition = -1

            setTitle("My AlertDialog con lista simple")

            setSingleChoiceItems(R.array.array_nombres, -1) { _, which ->
                selectedPosition = which
                Log.d("DEBUG", names[selectedPosition])
            }

            setPositiveButton(android.R.string.ok) { dialog, _ ->
                if (selectedPosition != -1) {
                    Toast.makeText(context, names[selectedPosition], Toast.LENGTH_SHORT).show()
                }
            }

            setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(context, android.R.string.cancel, Toast.LENGTH_SHORT).show()
            }
        }.show()
    }

    private fun myAlertDialogList(names: Array<String>) {
        AlertDialog.Builder(this).apply {
            setTitle("My AlertDialog con lista")
            setItems(names) { _, which ->
                Toast.makeText(
                    context,
                    names[which],
                    Toast.LENGTH_LONG
                ).show()
            }
        }.show()
    }

    private val actionButton = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this, android.R.string.ok, Toast.LENGTH_SHORT).show()
        binding.root.setBackgroundColor(Color.GREEN)
    }

    private fun myAlertDialog(message: CharSequence) {
        // Se crea el AlertDialog.
        //AlertDialog.Builder(this).apply {
        MaterialAlertDialogBuilder(this).apply {
            // Se asigna un título.
            setTitle(android.R.string.dialog_alert_title)
            // Se asgina el cuerpo del mensaje.
            setMessage(message)
            // Se define el comportamiento de los botones.
            setPositiveButton(
                android.R.string.ok,
                DialogInterface.OnClickListener(function = actionButton)
            )
            setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                binding.root.setBackgroundColor(Color.RED)
            }
            setNeutralButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(context, android.R.string.cancel, Toast.LENGTH_SHORT).show()
                binding.root.setBackgroundColor(Color.WHITE)
            }
        }.show() // Se muestra el AlertDialog.
    }
}