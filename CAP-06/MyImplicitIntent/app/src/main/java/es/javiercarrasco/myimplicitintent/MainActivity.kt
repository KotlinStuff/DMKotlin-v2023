package es.javiercarrasco.myimplicitintent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.javiercarrasco.myimplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Constante que contiene el valor asignado al permiso de la app.
    private val MY_PERMISSIONS_REQUEST_CALL_PHONE = 234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWebPage.setOnClickListener {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.javiercarrasco.es")
            ).apply {
                if (this.resolveActivity(packageManager) != null)
                    startActivity(this)
                else Log.d(
                    "DEBUG",
                    "Hay un problema para encontrar un navegador."
                )
            }
        }

        // Realizar una llamada telefónica.
        binding.btnCallPhone.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permiso no concedido.
                Log.d("DEBUG", "No está concedido el permiso para llamar")

                // Si el usuario ya ha rechazado al menos una vez (TRUE), se da explicación.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.CALL_PHONE
                    )
                ) {
                    Log.d("DEBUG", "Se da una explicación")

                    MaterialAlertDialogBuilder(this)
                        .setTitle("Permiso para llamar")
                        .setMessage("Puede resultar interesante indicar porqué.")
                        .setNeutralButton(android.R.string.cancel) { dialog, which ->
                            dialog.cancel()
                        }
                        .setPositiveButton(android.R.string.ok) { dialog, which ->
                            Log.d("DEBUG", "Se acepta y se vuelve a pedir permiso")

                            ActivityCompat.requestPermissions(
                                this, arrayOf(Manifest.permission.CALL_PHONE),
                                MY_PERMISSIONS_REQUEST_CALL_PHONE
                            )
                        }
                        .show()
                } else { // No requiere explicación, se pregunta por el permiso.
                    Log.d("DEBUG", "No se da una explicación.")
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_CALL_PHONE
                    )
                }
            } else {
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:965555555")
                )
                startActivity(intent)
            }
        } // Fin btnCallphone.setOnClickListener
    }

    // Se analiza la respuesta del usuario a la petición de permisos.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {
                if ((grantResults.isNotEmpty() && grantResults[0]
                            == PackageManager.PERMISSION_GRANTED)
                )
                    Log.d("DEBUG", "Permiso concedido!!")
                else
                    Log.d("DEBUG", "Permiso rechazado!!")

                return
            }
            else -> Log.d("DEBUG", "Se pasa de los permisos.")
        }
    }
}