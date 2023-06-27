package es.javiercarrasco.myokhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkConnection(this)) {
            var pageContent = ""
            lifecycleScope.launch {
                pageContent = downloadWebPage("https://www.javiercarrasco.es/")
                println(pageContent)
                binding.textView.text = pageContent
            }
        } else binding.textView.text = getString(R.string.ConnectionOk)
    }

    suspend fun downloadWebPage(url: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder() // import okhttp3.Request
                    .url(url)
                    .build()
                val response = OkHttpClient().newCall(request).execute()
                response.body!!.string()
            } catch (e: IOException) {
                val response = e.message
                "ERROR: $response"
            }
        }
    }
}