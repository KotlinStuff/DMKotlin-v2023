package es.javiercarrasco.myretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.javiercarrasco.myretrofit.data.Retrofit2Api
import es.javiercarrasco.myretrofit.data.StoreDataSource
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            StoreRepository(StoreDataSource()).fetchProducts().flowOn(Dispatchers.IO)
                .catch {
                    binding.textView.text = it.toString()
                }
                .collect {
                    binding.textView.text = ""
                    it.forEach {
                        binding.textView.append("${it.id} - ${it.title}\n")
                    }
                }
        }
    }
}