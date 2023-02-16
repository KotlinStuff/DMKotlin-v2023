package es.javiercarrasco.myimplicitintent

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.util.Log
import android.util.Log.INFO
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import es.javiercarrasco.myimplicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionManager: PermissionManager

    // Constante que contiene el valor asignado al permiso de la app.
    private val MY_PERMISSIONS_REQUEST_CODE = 234

    private var resultCaptura = registerForActivityResult(StartActivityForResult()) { result ->
        val data: Intent? = result.data

        if (result.resultCode == RESULT_OK) {
            val thumbnail: Bitmap = data?.getParcelableExtra("data")!!
            binding.imageView.setImageBitmap(thumbnail)
        }
    }

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
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DEBUG", "El permiso ya está concedido.")
                val intent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:965555555")
                )
                startActivity(intent)
            } else {
                permissionManager = PermissionManager(
                    this,
                    Manifest.permission.CALL_PHONE,
                    MY_PERMISSIONS_REQUEST_CODE
                )
                permissionManager.checkPermissions()
            }
        }

        // Abrir la cámara de fotos.
        binding.btnTakePicture.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DEBUG", "El permiso ya está concedido.")

                val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                startActivity(intent)
            } else {
                permissionManager = PermissionManager(
                    this,
                    Manifest.permission.CAMERA,
                    MY_PERMISSIONS_REQUEST_CODE
                )
                permissionManager.checkPermissions()
            }
        }

        binding.btnAddAlarm.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "Se acabó dormir")
                .putExtra(AlarmClock.EXTRA_HOUR, 7)
                .putExtra(AlarmClock.EXTRA_MINUTES, 45)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        binding.btnSendSMS.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:" + 777666777)
                putExtra("sms_body", "Cuerpo del mensaje")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        binding.btnSendEmail.setOnClickListener {
            val TO = arrayOf("javier@javiercarrasco.es")
            val CC = arrayOf("")
            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/html" // o también text/plain
            intent.putExtra(Intent.EXTRA_EMAIL, TO)
            intent.putExtra(Intent.EXTRA_CC, CC)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Envío de un email desde Kotlin")
            intent.putExtra(Intent.EXTRA_TEXT, "Esta es mi prueba de envío de un correo.")

            startActivity(Intent.createChooser(intent, "Enviar correo..."))
        }

        // Capturar una imagen de la cámara.
        binding.btnTakePicture2.setOnClickListener {
            // Se comprueba si el permiso en cuestión está concedido.
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

                if (intent.resolveActivity(packageManager) != null)
                    resultCaptura.launch(intent)

            } else {
                permissionManager = PermissionManager(
                    this,
                    Manifest.permission.CAMERA,
                    MY_PERMISSIONS_REQUEST_CODE
                )
                permissionManager.checkPermissions()
            }
        }

        // Abrir Google Maps.
        binding.btnOpenMaps?.let {
            it.setOnClickListener {
                // Para abrir Google Maps, si no se requiere ubicación, no es
                // necesario solicitar permiso.
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=Alicante")
                )

                startActivity(intent)
            }
        }

        val estado = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            this.display?.rotation
        else {
            @Suppress("DEPRECATION")
            this.windowManager.defaultDisplay.rotation
        }
        Log.i("DISPLAY", estado.toString())
    }

    // Se analiza la respuesta del usuario a la petición de permisos.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {
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