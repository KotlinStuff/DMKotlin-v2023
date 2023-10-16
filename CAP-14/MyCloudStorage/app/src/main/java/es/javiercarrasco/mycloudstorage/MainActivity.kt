package es.javiercarrasco.mycloudstorage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import es.javiercarrasco.mycloudstorage.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()

        uploadFile()
    }

    private fun uploadFile() {
        val storageRef = storage.reference
        val imagesRef = storageRef.child("images")
        val fileImageRef = storageRef.child("images/prueba.jpg")

//        binding.imageView.isDrawingCacheEnabled = true
//        binding.imageView.buildDrawingCache()
        val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = fileImageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d("addOnFailureListener", "Fallo en la carga ", it)
        }.addOnSuccessListener {
            Log.d("addOnSuccessListener", "Uploaded file: ${it.metadata?.sizeBytes}")
        }
    }
}