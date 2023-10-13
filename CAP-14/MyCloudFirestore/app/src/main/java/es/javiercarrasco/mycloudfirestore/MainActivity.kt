package es.javiercarrasco.mycloudfirestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import es.javiercarrasco.mycloudfirestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val colProfesores: CollectionReference = db.collection("profesores")

        val docRef: DocumentReference = colProfesores.document("1")

        // página 15.
    }
}