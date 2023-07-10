package es.javiercarrasco.myretrofit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.javiercarrasco.myretrofit.adapters.ProductsRecyclerAdapter
import es.javiercarrasco.myretrofit.data.StoreDataSource
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by viewModels {
        val storeDataSource = StoreDataSource()
        val storeRepository = StoreRepository(storeDataSource)
        MainViewModelFactory(storeRepository)
    }

    private val adapter: ProductsRecyclerAdapter by lazy {
        ProductsRecyclerAdapter(onClickProduct = { product ->
            MaterialAlertDialogBuilder(this).apply {
                setTitle(product.title)
                setMessage(product.description)
                setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding.textView.text = getString(R.string.txtCargando)
        binding.recyclerProducts.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerProducts.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm._products
                    .catch {
                        println("ERROR: ${it.toString()}")
                    }
                    .collect {
                        adapter.submitList(it)
//                        binding.textView.text = ""
//                        it.forEach {
//                            binding.textView.append(
//                                "${it.id}\n" +
//                                        "${it.title}\n" +
//                                        "${it.category}\n" +
//                                        "${it.description}\n" +
//                                        "${it.price}\n" +
//                                        "${it.image}\n\n"
//                            )
//                        }
                    }
            }
        }

//        lifecycleScope.launch {
//            StoreRepository(StoreDataSource()).fetchProducts().flowOn(Dispatchers.IO)
//                .catch {
//                    binding.textView.text = it.toString()
//                }
//                .collect {
//                    binding.textView.text = ""
//                    it.forEach {
//                        binding.textView.append("${it.id} - ${it.title}\n")
//                    }
//                }
//        }
    }
}