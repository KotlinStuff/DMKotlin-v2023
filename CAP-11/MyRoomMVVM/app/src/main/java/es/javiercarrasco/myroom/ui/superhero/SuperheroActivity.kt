package es.javiercarrasco.myroom.ui.superhero

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import es.javiercarrasco.myroom.MyRoomApplication
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.data.SupersDataSource
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.databinding.ActivitySuperheroBinding
import es.javiercarrasco.myroom.domain.Editorial
import es.javiercarrasco.myroom.domain.SuperHero
import kotlinx.coroutines.launch

class SuperheroActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperheroBinding
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var editorialsList: List<Editorial>
    private var editorialsArray = ArrayList<String>()

    private val vm: SuperheroViewModel by viewModels {
        val db = (application as MyRoomApplication).supersDatabase
        val supersDataSource = SupersDataSource(db.supersDAO())
        val supersRepository = SupersRepository(supersDataSource)
        SuperheroViewModelFactory(supersRepository)
    }

    private var idSuper = -1
    private var superHero: SuperHero? = null
    private var editorialId = -1 // Versión ArrayAdapter

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

        // Se crea el adaptador mediante ArrayAdapter.
        adapter = ArrayAdapter(
            this@SuperheroActivity,
            android.R.layout.simple_list_item_activated_1,
            editorialsArray
        )

        // Se carga el adaptador en el Spinner.
        binding.spinner.adapter = adapter

// ********* Aplicando ArrayAdapter ************************************

        // Alternativa para evitar el problema de la des-subscripción.
        lifecycleScope.launch {
            // En este método se indica en que estado comenzará a recolectar (STARTED),
            // y en su opuesto (ON_STOP) se detendrá.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect {
                    editorialsList = it
                    editorialsArray.clear()

                    // Se crea un ArrayList<String> con los nombres de las editoriales.
                    it.forEach {
                        editorialsArray.add(it.name!!)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }

        showSuperhero()

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
                    /*CoroutineScope(Dispatchers.IO).launch {
                        db.supersDAO().insertSuperHero(
                            SuperHero(0, supername, realname, fab, editorialsList[editorialId].idEd)
                        )
                    }*/
                } else {
                    /*CoroutineScope(Dispatchers.IO).launch {
                        db.supersDAO().updateSuperHero(
                            SuperHero(
                                idSuper, supername, realname, fab, editorialsList[editorialId].idEd
                            )
                        )
                    }*/
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