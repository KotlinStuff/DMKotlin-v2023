package es.javiercarrasco.myokhttp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import es.javiercarrasco.myokhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkConnection(this)) {
            // Sin ViewModel
//            var pageContent = ""
//            lifecycleScope.launch {
//                pageContent = downloadWebPage("https://www.javiercarrasco.es/")
//                println(pageContent)
//                binding.textView.text = pageContent
//            }

            // Con ViewModel
            vm.pageContent.observe(this) {
                binding.textView.text = it
            }

        } else binding.textView.text = getString(R.string.ConnectionOk)
    }
}