package es.javiercarrasco.myroom.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myroom.MyRoomApplication
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.model.Editorial
import es.javiercarrasco.myroom.model.SuperHero
import es.javiercarrasco.myroom.databinding.ActivitySuperheroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuperheroActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperheroBinding
    private lateinit var db: SupersDatabase
    private lateinit var editorialsList: List<Editorial>

    private var idSuper = -1
    private var superHero: SuperHero? = null
    private var editorialId = -1 // Versión ArrayAdapter

    // private lateinit var cursor: Cursor // Versión SimpleCursorAdapter
    // private var cursorPos: Cursor? = null // Versión SimpleCursorAdapter

    companion object {
        const val EXTRA_SUPER_ID = "superId"
        fun navigate(activity: AppCompatActivity, superId: Int = -1) {
            activity.startActivity(
                Intent(
                    activity,
                    SuperheroActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).apply {
                    putExtra(EXTRA_SUPER_ID, superId)
                },
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = getString(R.string.txt_superhero)

        idSuper = intent.getIntExtra(EXTRA_SUPER_ID, -1)

        db = (application as MyRoomApplication).supersDatabase

// ********* Aplicando SimpleCursorAdapter ************************************
        /*    CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    cursor = db.supersDAO().getAllEditorials2()
                }

                // Se crea el adaptador mediante SimpleCursorAdapter.
                val adapter = SimpleCursorAdapter(
                    this@SuperheroActivity,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    arrayOf(cursor.columnNames[0], cursor.columnNames[1]),
                    intArrayOf(android.R.id.text1, android.R.id.text2),
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                )

                // Se carga el adaptador en el Spinner.
                binding.spinner.adapter = adapter
            } */
// ******** FIN SimpleCursorAdapter ******************************************

// ********* Aplicando ArrayAdapter ************************************
        CoroutineScope(Dispatchers.Main).launch {
            val adapter: ArrayAdapter<String>

            withContext(Dispatchers.IO) {
                editorialsList = db.supersDAO().getAllEditorials()
                superHero = db.supersDAO().getSuperById(idSuper)
            }

            Log.d("EDITORIALES", editorialsList.size.toString())

            // Se crea un ArrayList<String> con los nombres de las editoriales.
            val editorialsArray = ArrayList<String>().apply {
                editorialsList.forEach {
                    this.add(it.name!!)
                }
            }

            // Se crea el adaptador mediante ArrayAdapter.
            adapter = ArrayAdapter(
                this@SuperheroActivity,
                android.R.layout.simple_list_item_activated_1,
                editorialsArray
            )

            // Se carga el adaptador en el Spinner.
            binding.spinner.adapter = adapter

            showSuperhero()
        }
// ******** FIN ArrayAdapter ******************************************
    }

    override fun onStart() {
        super.onStart()

        binding.button.setOnClickListener {
            if (binding.etSuperName.text.isNullOrBlank())
                binding.labelEtSuperName.error = getString(R.string.warning_empty_field)
            else if (binding.etRealName.text.isNullOrBlank())
                binding.labelEtRealName.error = getString(R.string.warning_empty_field)
            else {
                binding.labelEtSuperName.error = null
                val supername = binding.etSuperName.text!!.trim().toString()
                val realname = binding.etRealName.text!!.trim().toString()
                val fab = if (binding.switchFab.isChecked) 1 else 0

                if (idSuper == -1) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.supersDAO().insertSuperHero(
                            SuperHero(0, supername, realname, fab, editorialsList[editorialId].idEd)
                        )
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.supersDAO().updateSuperHero(
                            SuperHero(
                                idSuper, supername, realname, fab, editorialsList[editorialId].idEd
                            )
                        )
                    }
                }

                finish()
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                // Versión ArrayAdapter
                editorialId = pos
                Log.d(
                    "Spinner",
                    "${editorialsList[pos].idEd} - ${editorialsList[pos].name}"
                )

                // Versión SimpleCursorAdapter
                /* cursorPos = binding.spinner.getItemAtPosition(pos) as Cursor
                Log.d(
                    "Spinner",
                    "${cursorPos!!.position} - ${cursorPos!!.getString(1)}"
                )*/
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun showSuperhero() {
        // Se abre detalle del ítem.
        if (idSuper != -1) {
            if (superHero != null) {
                binding.etSuperName.setText(superHero!!.superName)
                binding.etRealName.setText(superHero!!.realName)

                binding.spinner.setSelection(
                    editorialsList.withIndex().first {
                        it.value.idEd == superHero!!.idEditorial
                    }.index
                )

                binding.switchFab.isChecked = superHero!!.favorite == 1
            }
        }
    }
}