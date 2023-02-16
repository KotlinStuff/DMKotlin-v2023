package es.javiercarrasco.myexplicitintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myexplicitintent.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se recuperan los datos y se asignan al TextView.
        intent.getStringExtra(MainActivity.EXTRA_MESSAGE).apply {
            binding.tvMsgReceived.text = this
        }
    }
}