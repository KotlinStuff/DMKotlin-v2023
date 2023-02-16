package es.javiercarrasco.mylitleviewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mylitleviewmodel.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setSubtitle(
            getString(R.string.txt_subtitle) +
                    " - " +
                    this.localClassName
        )
    }
}