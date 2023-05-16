package es.javiercarrasco.myroom.ui.editorial

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myroom.MyRoomApplication
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.data.SupersDataSource
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.databinding.ActivityEditorialBinding
import es.javiercarrasco.myroom.domain.SaveEditorialUseCase

class EditorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorialBinding

    private val vm: EditorialViewModel by viewModels {
        val db = (application as MyRoomApplication).supersDatabase
        val supersDataSource = SupersDataSource(db.supersDAO())
        val supersRepository = SupersRepository(supersDataSource)
        val saveEditorialUseCase = SaveEditorialUseCase(supersRepository)

        EditorialViewModelFactory(saveEditorialUseCase)
    }

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
                vm.save(binding.etEditorial.text!!.trim().toString())

                finishAfterTransition()
            }
        }
    }
}