package es.javiercarrasco.myimplicitintent

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myimplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Callback encargado de recoger el thumbnail de la captura.
    private var resultCaptura = registerForActivityResult(StartActivityForResult()) { result ->
        val data: Intent? = result.data

        if (result.resultCode == RESULT_OK) {
            val thumbnail: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                data?.getParcelableExtra("data", Bitmap::class.java)
            else data?.getParcelableExtra("data")

            binding.imageView.setImageBitmap(thumbnail)
            binding.imageView.visibility = View.VISIBLE
        }
    }

    // Este callback se encargará de recoger la respuesta del usuario a la petición del permiso.
    private val requestPermissionCallPhone =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission is granted.
                Toast.makeText(this, "Ya puedes llamar", Toast.LENGTH_SHORT).show()
            } else {
                // Se daría una explicación al usuario sobre las consecuencias de denegar el permiso.
                Toast.makeText(
                    this,
                    "Sin conceder este permiso no puedes llamar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    // Este callback se encargará de recoger la respuesta del usuario a la petición del permiso.
    private val requestPermissionCamera =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission is granted.
                Toast.makeText(this, "Ya puedes hacer fotos", Toast.LENGTH_SHORT).show()
            } else {
                // Se daría una explicación al usuario sobre las consecuencias de denegar el permiso.
                Toast.makeText(
                    this,
                    "Sin conceder este permiso no puedes hacer fotos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

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

        // Enviar un correo electrónico
        binding.btnSendEmail.setOnClickListener {
            val TO = arrayOf("javier@javiercarrasco.es")
            val CC = arrayOf("")

            Intent(Intent.ACTION_SEND).apply {
                type = "text/html" // o también text/plain
                putExtra(Intent.EXTRA_EMAIL, TO)
                putExtra(Intent.EXTRA_CC, CC)
                putExtra(Intent.EXTRA_SUBJECT, "Envío de un email desde Kotlin")
                putExtra(Intent.EXTRA_TEXT, "Esta es mi prueba de envío de un correo.")

                if (this.resolveActivity(packageManager) != null)
                    startActivity(Intent.createChooser(this, "Enviar correo..."))
                else Log.d("DEBUG", "Hay un problema para enviar el correo electrónico.")
            }
        }

        // Abrir la aplicación de mapas.
        binding.btnOpenMaps.setOnClickListener {
            // Para abrir Google Maps, si no se requiere ubicación, no es necesario solicitar permiso.
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=38.34,-0.49(Alicante)"))
            )
        }

        // Establece una alarma.
        binding.btnAlarm.setOnClickListener {
            Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Se acabó dormir")
                putExtra(AlarmClock.EXTRA_HOUR, 7)
                putExtra(AlarmClock.EXTRA_MINUTES, 45)

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(this)
                }
            }
        }

        // Realizar una llamada telefónica.
        binding.btnCallPhone.setOnClickListener {
            if (PermissionHandler(this).checkPermission(Manifest.permission.CALL_PHONE)) {
                Intent(Intent.ACTION_CALL, Uri.parse("tel:965555555")).apply {
                    startActivity(this)
                }
            } else {
                requestPermissionCallPhone.launch(Manifest.permission.CALL_PHONE)
            }
        }

        // Hacer una foto.
        binding.btnTakePicture.setOnClickListener {
            binding.imageView.visibility = View.GONE

            if (PermissionHandler(this).checkPermission(Manifest.permission.CAMERA)) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                if (intent.resolveActivity(packageManager) != null)
                    resultCaptura.launch(intent)

            } else {
                requestPermissionCamera.launch(Manifest.permission.CAMERA)
            }
        }

    }
}