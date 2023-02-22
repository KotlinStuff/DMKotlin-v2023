package es.javiercarrasco.mypermissionhandler

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mypermissionhandler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var resultCaptura = registerForActivityResult(StartActivityForResult()) { result ->
        val data: Intent? = result.data

        if (result.resultCode == RESULT_OK) {
            val thumbnail: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra("data", Bitmap::class.java)
            } else {
                data?.getParcelableExtra("data")
            }

            binding.imageView.setImageBitmap(thumbnail)
        }
    }

    // Este callback se encargará de recoger la respuesta del usuario a la petición del permiso.
    val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission is granted.
                binding.tvStatePermission.text =
                    getString(R.string.txt_permissionState, "granted")
            } else {
                // Se daría una explicación al usuario sobre las consecuencias de denegar el permiso.
                binding.tvStatePermission.text =
                    getString(R.string.txt_permissionState, "no granted")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.button.setOnClickListener {
            if (PermissionHandler(this).checkPermission(Manifest.permission.CAMERA)) {
                binding.tvStatePermission.text = getString(R.string.txt_permissionState, "granted")
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                if (intent.resolveActivity(packageManager) != null)
                    resultCaptura.launch(intent)

            } else {
                binding.tvStatePermission.text =
                    getString(R.string.txt_permissionState, "no granted")
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}