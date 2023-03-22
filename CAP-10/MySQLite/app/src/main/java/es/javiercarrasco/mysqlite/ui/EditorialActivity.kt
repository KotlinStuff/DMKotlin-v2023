package es.javiercarrasco.mysqlite.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mysqlite.MySQLiteApplication
import es.javiercarrasco.mysqlite.R
import es.javiercarrasco.mysqlite.data.SupersDBHelper
import es.javiercarrasco.mysqlite.databinding.ActivityEditorialBinding

class EditorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorialBinding

    companion object {
        fun navigate(activity: AppCompatActivity) {
            activity.startActivity(
                Intent(
                    activity,
                    EditorialActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = getString(R.string.txt_editorial)

        binding.button.setOnClickListener {
            if (binding.etEditorial.text.isNullOrBlank())
                binding.labelEtEditorial.error = getString(R.string.warning_empty_field)
            else {
                binding.labelEtEditorial.error = null
                val name = binding.etEditorial.text!!.trim().toString()
                (application as MySQLiteApplication).supersDBHelper.addEditorial(name)
                finish()
            }
        }
    }
}