package es.javiercarrasco.myroom.ui

import android.app.ActivityOptions
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myroom.MyRoomApplication
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.data.model.Editorial
import es.javiercarrasco.myroom.data.model.SuperHero
import es.javiercarrasco.myroom.databinding.ActivitySuperheroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuperheroActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperheroBinding
    private lateinit var db: SupersDatabase

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

        db = (application as MyRoomApplication).supersDatabase

        supportActionBar!!.title = getString(R.string.txt_superhero)

        var editorialsList = emptyList<Editorial>()
        val adapter = ArrayAdapter(
            this@SuperheroActivity,
            android.R.layout.simple_list_item_2,
            editorialsList
        )

        CoroutineScope(Dispatchers.IO).launch {
            editorialsList = db.supersDAO().getAllEditorials()
            // Se crea el adaptador mediante SimpleCursorAdapter.
        }

        // Se carga el adaptador en el Spinner.
        binding.spinner.adapter = adapter


        // Se abre detalle del ítem.
        /*val idSuper = intent.getIntExtra(EXTRA_SUPER_ID, -1)
        if (idSuper != -1) {
            val superHero =
                (application as MyRoomApplication).supersDatabase.getSuperById(idSuper)

            binding.etSuperName.setText(superHero.superName)
            binding.etRealName.setText(superHero.realName)

            var pos = 0
            cursor.moveToFirst()
            do {
                // Se busca la posición de la Editorial en el cursor.
                if (cursor.getInt(0) == superHero.editorial.id)
                    pos = cursor.position
            } while (cursor.moveToNext())

            binding.spinner.setSelection(pos)

            binding.switchFab.isChecked = superHero.favorite == 1
        }

        var cursorPos: Cursor? = null
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                cursorPos = binding.spinner.getItemAtPosition(pos) as Cursor
                Log.d(
                    "Spinner",
                    "${cursorPos!!.position} - ${cursorPos!!.getString(1)}"
                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

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
                    (application as MyRoomApplication).supersDatabase.addSuperHero(
                        SuperHero(0, supername, realname, fab, Editorial(cursorPos!!.getInt(0)))
                    )
                } else {
                    (application as MyRoomApplication).supersDatabase.updateSuperHero(
                        SuperHero(
                            idSuper, supername, realname, fab, Editorial(cursorPos!!.getInt(0))
                        )
                    )
                }

                finish()
            }
        }*/
    }
}