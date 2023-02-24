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

        val students: ArrayList<Student>?

        students = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("STUDENT", Student::class.java) as ArrayList<Student>
        else intent.getSerializableExtra("STUDENT") as ArrayList<Student>

        students.forEach {
            binding.tvResult.append("${it.idStudent}\n")
            binding.tvResult.append("${it.name}\n")
            binding.tvResult.append("${it.surname}\n")
            binding.tvResult.append("${it.age}\n\n")
        }
    }
}