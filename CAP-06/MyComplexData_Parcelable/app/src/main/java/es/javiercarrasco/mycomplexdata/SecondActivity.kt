package es.javiercarrasco.mycomplexdata

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.mycomplexdata.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student: ArrayList<Student>?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            student = intent.getParcelableArrayListExtra("STUDENT", Student::class.java)
        else student = intent.getParcelableArrayListExtra("STUDENT")!!

        student!!.forEach {
            binding.tvResult.append("${it.idStudent}\n")
            binding.tvResult.append("${it.name}\n")
            binding.tvResult.append("${it.surname}\n")
            binding.tvResult.append("${it.age}\n\n")
        }
    }
}