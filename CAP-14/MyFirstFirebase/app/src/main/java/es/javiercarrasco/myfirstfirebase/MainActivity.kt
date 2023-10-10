package es.javiercarrasco.myfirstfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.javiercarrasco.myfirstfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase
            .getInstance("https://my-first-firebase-projec-8738d-default-rtdb.europe-west1.firebasedatabase.app")
            .reference

        // Referencia al nodo "mascotas".
        val dbfMascotas = database.child("mascotas")
        // Referencia al nodo "gato".
        val dbfGato = dbfMascotas.child("gato")
        val dbfNombre = dbfGato.child("nombre")

        // Se crea un listener para que sean notificados los cambios en el nombre.
        dbfGato.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvNombre.text =
                    getString(R.string.txt_nombre, snapshot.child("nombre").value)
                binding.tvRaza.text = getString(R.string.txt_raza, snapshot.child("raza").value)
            }

            // Se llama cuando se cancela la lectura, o se produce un error.
            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Error al leer el valor de nombre.")
            }
        })

        // Se obtiene la referencia a los usuarios.
        val dbfUsers = database.child("usuarios")

        dbfUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users: MutableList<User> = mutableListOf()

                binding.tvUsersList.text = null
                for (userSnapshot in snapshot.children) {
                    users.add(userSnapshot.getValue(User::class.java)!!)

                    binding.tvUsersList.append(
                        "${userSnapshot.getValue(User::class.java)!!.name} " +
                                "${userSnapshot.getValue(User::class.java)!!.surname}\n"
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("onCancelled", "Error!", error.toException())
            }
        })
    }

    override fun onStart() {
        super.onStart()

        // Añadir usuarios a Firebase.
        binding.btnAddUsers.setOnClickListener {
            val users: MutableList<User> = mutableListOf()

            users.add(User("Javier", "Carrasco"))
            users.add(User("Nacho", "Cabanes"))
            users.add(User("Patricia", "Aracil"))
            users.add(User("Juan", "Palomo"))
            users.add(User("Raquel", "Sánchez"))

            database.child("usuarios").setValue(users)
        }

        // Actualizar usuario.
        binding.btnUpdateUsers.setOnClickListener {
            val userUpdate = HashMap<String, Any>()
            userUpdate["0"] = User("Javi", "Hernández")

            database.child("usuarios").updateChildren(userUpdate)
        }

        // Eliminar usuario.
        binding.btnDelUser.setOnClickListener {
            database.child("usuarios")
                .child("0")
                .removeValue()
        }
    }
}

data class User(val name: String = "", val surname: String = "")
