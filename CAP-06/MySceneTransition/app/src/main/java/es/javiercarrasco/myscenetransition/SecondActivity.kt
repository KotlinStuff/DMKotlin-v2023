package es.javiercarrasco.myscenetransition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myscenetransition.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            title = getString(R.string.app_name2)
        }

        binding.linear.setOnClickListener {
            finishAfterTransition()
        }
    }
}