package es.javiercarrasco.mycloudstorage

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import es.javiercarrasco.mycloudstorage.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()

        val storageRef: StorageReference = storage.reference // Raíz del Storage
        val imagesRef: StorageReference = storageRef.child("images") // Carpeta images
        val fileImageRef: StorageReference = imagesRef.child("prueba.jpg") // Fichero prueba.jpg
        val fileArbolRef: StorageReference = imagesRef.child("arbol.jpg") // Fichero arbol.jpg

        Log.i("storageRef", "$storageRef")
        Log.i("imagesRef", "$imagesRef")
        Log.i("fileImageRef", "$fileImageRef")

        Log.i("fileImageRef.parent", "${fileImageRef.parent}")

        downloadFile(fileArbolRef)
    }

    private fun downloadFile(file: StorageReference) {
        val localFile: File = File.createTempFile("images", "jpg")

        file.getFile(localFile).addOnSuccessListener {
            Log.d("addOnSuccessListener", "Bytes downloaded: ${it.totalByteCount}")
            Log.d("Path temp file", "Download file: ${localFile}")

            val salida: OutputStream
            try {
                salida = openFileOutput(
                    file.name,
                    Activity.MODE_PRIVATE
                )
                salida.write(localFile.readBytes())
                salida.flush()
                salida.close()

                localFile.delete() // Borra el fichero temporal
            } catch (e: Exception) {
                Log.d("Exception", "Error al escribir el fichero", e)
            }

            Log.d("Path local file", "${filesDir.path}/${file.name}")
            Glide.with(this)
                .load("${filesDir.path}/${file.name}") // Carga la imagen desde files
                .override(binding.imageView.width)
                .fitCenter()
                .into(binding.imageView)

        }.addOnFailureListener {
            Log.d("addOnFailureListener", "Fallo en la descarga ", it)
        }
    }

    private fun uploadFile(file: StorageReference) {

        val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask: UploadTask = file.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d("addOnFailureListener", "Fallo en la carga ", it)
        }.addOnSuccessListener {
            Log.d("addOnSuccessListener", "Uploaded file: ${it.metadata?.sizeBytes}")
        }
    }
}