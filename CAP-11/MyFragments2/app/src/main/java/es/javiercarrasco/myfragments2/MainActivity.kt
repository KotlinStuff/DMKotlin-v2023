package es.javiercarrasco.myfragments2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import es.javiercarrasco.myfragments2.databinding.ActivityMainBinding

internal const val ARG_NUMFRAG = "numFrag"
internal const val ARG_COLORBACK = "colorBack"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}