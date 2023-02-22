package es.javiercarrasco.myimplicitintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myimplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Abrir el navegador con una URL.
        binding.btnWebPage.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.javiercarrasco.es")).apply {
                if (this.resolveActivity(packageManager) != null)
                    startActivity(this)
                else Log.d("DEBUG", "Hay un problema para encontrar un navegador.")
            }
        }

        // Marcar un número de teléfono.
        binding.btnDialPhone.setOnClickListener {
            val dial = "tel: "
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial + "+34777111222")))
        }

        // Enviar un SMS
        binding.btnSendSMS.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:777666777")
                putExtra("sms_body", "Cuerpo del mensaje")

                // if (this.resolveActivity(packageManager) != null)
                startActivity(this)
                // else Log.d("DEBUG", "Hay un problema para enviar el SMS.")
            }
        }

        // Abrir la aplicación de mapas.
        binding.btnOpenMaps.setOnClickListener {
            // Para abrir Google Maps, si no se requiere ubicación, no es necesario solicitar permiso.
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=38.34,-0.49(Alicante)"))
            )
        }

        // Realizar una llamada telefónica.
//        binding.btnCallPhone.setOnClickListener {
        // Se comprueba si el permiso en cuestión está concedido.
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.CALL_PHONE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // Permiso no concedido.
//                Log.d("DEBUG", "No está concedido el permiso para llamar")
//
//                // Si el usuario ya ha rechazado al menos una vez (TRUE), se da explicación.
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        this, Manifest.permission.CALL_PHONE
//                    )
//                ) {
//                    Log.d("DEBUG", "Se da una explicación")
//
//                    MaterialAlertDialogBuilder(this)
//                        .setTitle("Permiso para llamar")
//                        .setMessage("Puede resultar interesante indicar porqué.")
//                        .setNeutralButton(android.R.string.cancel) { dialog, which ->
//                            dialog.cancel()
//                        }
//                        .setPositiveButton(android.R.string.ok) { dialog, which ->
//                            Log.d("DEBUG", "Se acepta y se vuelve a pedir permiso")
//
//                            ActivityCompat.requestPermissions(
//                                this, arrayOf(Manifest.permission.CALL_PHONE),
//                                MY_PERMISSIONS_REQUEST_CALL_PHONE
//                            )
//                        }
//                        .show()
//                } else { // No requiere explicación, se pregunta por el permiso.
//                    Log.d("DEBUG", "No se da una explicación.")
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(Manifest.permission.CALL_PHONE),
//                        MY_PERMISSIONS_REQUEST_CALL_PHONE
//                    )
//                }
//            } else {
//                Log.d("DEBUG", "El permiso ya está concedido.")
//                val intent = Intent(
//                    Intent.ACTION_CALL,
//                    Uri.parse("tel:965555555")
//                )
//                startActivity(intent)
//            }
//        } // Fin btnCallphone.setOnClickListener
//        }
    }
}