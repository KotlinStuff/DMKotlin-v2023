package es.javiercarrasco.mycloudfirestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import es.javiercarrasco.mycloudfirestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val colComunidades = db.collection("comunidades")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se obtienen todos los documentos de la colección (con escucha) y se rellena el TextView.
        colComunidades.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.w("DOC_SHOW", "Escucha fallida!.", e)
                return@addSnapshotListener
            }

            binding.textView.text = ""
            for (document in querySnapshot!!) {
                Log.d("DOC_SHOW", "${document.id} => ${document.data}")
                binding.textView.append(
                    "${document!!["comunidad"]}\n" +
                            "\tCapital: ${document["datos.capital"]}\n" +
                            "\tHabs: ${document["datos.habitantes"]}\n" +
                            "\tProvincias: ${document["provincias"]}\n\n"
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Botón para añadir documentos a la colección.
        binding.btnCreate.setOnClickListener {
            // Se prepara la estructura de datos.
            val comunidades = listOf(
                mapOf( // Comunidad 1
                    "comunidad" to "Andalucía",
                    "datos" to mapOf(
                        "capital" to "Sevilla",
                        "habitantes" to 688711
                    ),
                    "provincias" to listOf(
                        "Almería", "Granada", "Córdoba", "Jaén",
                        "Sevilla", "Málaga", "Cádiz", "Huelva"
                    )
                ), mapOf( // Comunidad 2
                    "comunidad" to "Comunidad Valenciana",
                    "datos" to mapOf(
                        "capital" to "Valencia",
                        "habitantes" to 791413
                    ),
                    "provincias" to listOf("Castellón", "Valencia", "Alicante")
                ), mapOf( // Comunidad 3
                    "comunidad" to "Aragón",
                    "datos" to mapOf(
                        "capital" to "Zaragoza",
                        "habitantes" to 666880
                    ),
                    "provincias" to listOf("Huesca", "Zaragoza", "Teruel")
                )
            )

            // Se añade documento a documento.
            for (doc in comunidades) {
                Log.d("DOC_ADD", "Añadiendo documento " + doc["comunidad"])
                colComunidades.document(doc["comunidad"].toString()).set(doc)
                    .addOnSuccessListener { Log.d("DOC_ADD", "Documento añadido correctamente") }
                    .addOnFailureListener { e ->
                        Log.w(
                            "DOC_ADD",
                            "Error al añadir el documento", e
                        )
                    }
            }
        }

        // Botón para eliminar documentos de la colección.
        binding.btnDelete.setOnClickListener {
            borrarDocumento("Andalucía")
            borrarDocumento("Aragón")
            borrarDocumento("Comunidad Valenciana")
        }
    }

    // Borra el documento indicando el ID por parámetro.
    fun borrarDocumento(id: String) {
        // Elimina documento a documento, para ello, se necesita conocer su identificador.
        colComunidades.document(id)
            .delete()
            .addOnSuccessListener { Log.d("DOC_DEL", "Documento eliminado correctamente") }
            .addOnFailureListener { e -> Log.w("DOC_DEL", "Error al eliminar el documento", e) }
    }
}