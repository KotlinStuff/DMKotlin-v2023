package es.javiercarrasco.myscenetransition

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myscenetransition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linear.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)

            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                binding.linear, "desliza",
            )

            startActivity(intent, options.toBundle())
        }
    }
}