package es.javiercarrasco.myroom.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.CoroutinesRoom
import es.javiercarrasco.myroom.MyRoomApplication
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.data.model.Editorial
import es.javiercarrasco.myroom.databinding.ActivityEditorialBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorialBinding
    private lateinit var db: SupersDatabase

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

        db = (application as MyRoomApplication).supersDatabase

        supportActionBar!!.title = getString(R.string.txt_editorial)

        binding.button.setOnClickListener {
            if (binding.etEditorial.text.isNullOrBlank())
                binding.labelEtEditorial.error = getString(R.string.warning_empty_field)
            else {
                binding.labelEtEditorial.error = null

                val name = binding.etEditorial.text!!.trim().toString()
                val newEditorial = Editorial(name = name)

                CoroutineScope(Dispatchers.IO).launch {
                    db.supersDAO().insertEditorial(newEditorial)
                }

                finish()
            }
        }
    }
}