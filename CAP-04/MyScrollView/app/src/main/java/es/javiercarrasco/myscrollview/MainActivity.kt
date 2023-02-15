package es.javiercarrasco.myscrollview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.myscrollview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}