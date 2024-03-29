package es.javiercarrasco.mysqlite.ui

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
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.mysqlite.MySQLiteApplication
import es.javiercarrasco.mysqlite.R
import es.javiercarrasco.mysqlite.data.model.Editorial
import es.javiercarrasco.mysqlite.data.model.SuperHero
import es.javiercarrasco.mysqlite.databinding.ActivitySuperheroBinding

class SuperheroActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperheroBinding

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

        val cursor = (application as MySQLiteApplication).supersDBHelper.getEditorialsCursor()

        // Se crea el adaptador mediante SimpleCursorAdapter.
        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf(cursor.columnNames[0], cursor.columnNames[1]),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        // Se carga el adaptador en el Spinner.
        binding.spinner.adapter = adapter

        // Se abre detalle del ítem.
        val idSuper = intent.getIntExtra(EXTRA_SUPER_ID, -1)
        if (idSuper != -1) {
            val superHero =
                (application as MySQLiteApplication).supersDBHelper.getSuperById(idSuper)

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
                    (application as MySQLiteApplication).supersDBHelper.addSuperHero(
                        SuperHero(0, supername, realname, fab, Editorial(cursorPos!!.getInt(0)))
                    )
                } else {
                    (application as MySQLiteApplication).supersDBHelper.updateSuperHero(
                        SuperHero(
                            idSuper, supername, realname, fab, Editorial(cursorPos!!.getInt(0))
                        )
                    )
                }

                finish()
            }
        }
    }
}