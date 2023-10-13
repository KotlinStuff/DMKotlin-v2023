package es.javiercarrasco.mycloudfirestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import es.javiercarrasco.mycloudfirestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var colProfesores: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        colProfesores = db.collection("profesores")

        val docRef: DocumentReference = colProfesores.document("1")

        // SEXTO MÉTODO DE OBTENCIÓN DE DATOS (TODOS LOS DOCUMENTOS)
        // Obtiene todos los documentos de una colección filtrados (sin escucha).
        colProfesores.whereEqualTo("modulo", "PMDM").get().apply {
            addOnSuccessListener {
                binding.textView.text = getString(R.string.txt_no_escucha)
                for (doc in it) {
                    Log.d("DOC", "${doc.id} => ${doc.data}")
                    binding.textView.append(
                        "${doc["modulo"]} - ${doc["nombre"]} ${doc["apellido"]}\n"
                    )
                }
            }

            addOnFailureListener { exception ->
                Log.d("DOC", "Error durante la recogida de documentos: ", exception)
            }
        }

        // Obtiene todos los documentos de una colección filtrados (con escucha).
        colProfesores.whereEqualTo("modulo", "ED")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w("addSnapshotListener", "Escucha fallida!.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                binding.textView2.text = getString(R.string.txt_con_escucha)
                for (doc in querySnapshot!!) {
                    Log.d("DOC", "${doc.id} => ${doc.data}")
                    binding.textView2.append(
                        "${doc["modulo"]} - ${doc["nombre"]} ${doc["apellido"]}\n"
                    )
                }
            }

        // QUINTO MÉTODO DE OBTENCIÓN DE DATOS (TODOS LOS DOCUMENTOS)
        // Obtener todos los documentos de una colección (con escucha).
//        colProfesores.addSnapshotListener { querySnapshot, firestoreException ->
//            if (firestoreException != null) {
//                Log.w("addSnapshotListener", "Escucha fallida!.", firestoreException)
//                return@addSnapshotListener
//            }
//
//            binding.textView.text = ""
//            for (doc in querySnapshot!!) {
//                Log.d("DOC", "${doc.id} => ${doc.data}")
//                binding.textView.append("${doc["modulo"]} - ${doc["nombre"]} ${doc["apellido"]}\n")
//            }
//        }

        // CUARTO MÉTODO DE OBTENCIÓN DE DATOS (TODOS LOS DOCUMENTOS)
        // Obtener todos los documentos de una colección (sin escucha).
//        colProfesores.get().apply {
//            addOnSuccessListener {
//                for (doc in it) {
//                    Log.d("DOC", "${doc.id} => ${doc.data}")
//                    binding.textView.append(
//                        "${doc!!["modulo"]} - ${doc["nombre"]} ${doc["apellido"]}\n"
//                    )
//                }
//            }
//
//            addOnFailureListener { exception ->
//                Log.d("DOC", "Error durante la recogida de documentos: ", exception)
//            }
//        }

        // TERCER MÉTODO DE OBTENCIÓN DE DATOS
        // Escucha del documento, contrasta la caché con la base de datos.
//        docRef.addSnapshotListener { value, error ->
//            if (error != null) {    // Se comprueba si hay fallo.
//                Log.w("addSnapshotListener", "Escucha fallida!", error)
//                return@addSnapshotListener
//            }
//
//            if (value != null && value.exists()) {
//                Log.d("addSnapshotListener", "Información actual: ${value.data}")
//                val texto = "${value["modulo"]} - ${value["nombre"]} ${value["apellido"]}"
//                binding.textView.text = texto
//            } else Log.d("addSnapshotListener", "Información actual: null")
//        }

        // SEGUNDO MÉTODO DE OBTENCIÓN DE DATOS
//        docRef.get().apply {
//            // Se obtiene la información una vez conpletada la conexión.
//            addOnCompleteListener {
//                if (it.isSuccessful) {
//                    // Documento encontrado en la caché offline.
//                    val doc = it.result
//
//                    Log.d("addOnCompleteListener", "Document data: ${doc?.data}")
//
//                    val texto = "${doc!!["modulo"]} - ${doc["nombre"]} ${doc["apellido"]}"
//                    binding.textView.text = texto
//                } else Log.d("addOnCompleteListener", "Fallo de lectura ", it.exception)
//            }
//        }

        // PRIMER MÉTODO DE OBTENCIÓN DE DATOS
//        docRef.get().apply {
//            // Se obtiene la información, se lanza sin llegar a terminar la conexión.
//            addOnSuccessListener {
//                Log.d("addOnSuccessListener", "Cached document data: ${it.data}")
//
//                val texto = "${it["modulo"]} - ${it["nombre"]} ${it["apellido"]}"
//                binding.textView.text = texto
//            }
//
//            // Fallo de lectura.
//            addOnFailureListener { e ->
//                Log.d("addOnFailureListener", "Fallo de lectura ", e)
//            }
//        }
    }

    override fun onStart() {
        super.onStart()

        // PRIMER MÉTODO PARA AÑADIR DOCUMENTOS
//        binding.button.setOnClickListener {
//            // Se crea la estructura del documento.
//            val profe = hashMapOf(
//                "nombre" to "Miguel",
//                "apellido" to "López",
//                "modulo" to "ED"
//            )
//            // Se añade el documento sin indicar ID, dejando que Firebase genere el ID
//            // al añadir el documento. Para esta acción se recomienda add().
//            colProfesores.document().set(profe)
//                // Respuesta si ha sido correcto.
//                .addOnSuccessListener {
//                    Log.d("DOC_SET", "Documento añadido!")
//                }
//                // Respuesta si se produce un fallo.
//                .addOnFailureListener { e ->
//                    Log.w("DOC_SET", "Error en la escritura", e)
//                }
//        }

        // SEGUNDO MÉTODO PARA AÑADIR DOCUMENTOS
//        binding.button.setOnClickListener {
//            // Se crea la estructura del documento.
//            val profe = hashMapOf(
//                "nombre" to "Miguel",
//                "apellido" to "López",
//                "modulo" to "ED"
//            )
//
//            colProfesores.document("7").set(profe)
//                // Respuesta si ha sido correcto.
//                .addOnSuccessListener {
//                    Log.d("DOC_SET", "Documento añadido!")
//                }
//                // Respuesta si se produce un fallo.
//                .addOnFailureListener { e ->
//                    Log.w("DOC_SET", "Error en la escritura", e)
//                }
//        }

        // TERECER MÉTODO PARA AÑADIR DOCUMENTOS
//        binding.button.setOnClickListener {
//            val colModulos = db.collection("modulos")  // Crea la colección si no existe.
//            val moduloNuevo = hashMapOf(
//                "siglas" to "PMDM",
//                "nombre" to "Programación Multimedia y Dispositivos Móviles"
//            )
//
//            colModulos.add(moduloNuevo)
//                .addOnSuccessListener { Log.d("DOC_ADD", "Documento añadido, id: ${it.id}") }
//                .addOnFailureListener { e -> Log.w("DOC_ADD", "Error añadiendo el documento", e) }
//        }

        // CUARTO MÉTODO PARA AÑADIR DOCUMENTOS
//        binding.button.setOnClickListener {
//            // Se crea un documento nuevo obteniendo su referencia.
//            val refModuloNuevo = db.collection("modulos").document()
//
//            val modulo = hashMapOf(
//                "abreviatura" to "ED",
//                "nombre" to "Entornos de Desarrollo"
//            )
//            refModuloNuevo.set(modulo)
//        }

        binding.button.setOnClickListener {
            // Se obtiene la referencia del documento a eliminar.
            val refModuloDel = db.collection("modulos").document("EAsjxpzHFQMT4w5DxwiS")

            refModuloDel
                .delete()
                .addOnSuccessListener { Log.d("DOC_DEL", "Documento eliminado correctamente") }
                .addOnFailureListener { e -> Log.w("DOC_DEL", "Error al eliminar el documento", e) }
        }
    }
}