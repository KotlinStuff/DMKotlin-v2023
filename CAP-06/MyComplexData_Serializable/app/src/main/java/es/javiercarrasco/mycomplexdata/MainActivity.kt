package es.javiercarrasco.mycomplexdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.javiercarrasco.mycomplexdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val students: ArrayList<Student> = arrayListOf(
            Student(1, "Javier", "Carrasco", 45),
            Student(2, "Patricia", "Aracil", 44),
            Student(3, "Nicolás", "Royo", 43)
        )

        binding.btnPasar.setOnClickListener {
            Intent(this, SecondActivity::class.java).apply {
                putExtra("STUDENT", students)

                startActivity(this)
            }
        }
    }
}