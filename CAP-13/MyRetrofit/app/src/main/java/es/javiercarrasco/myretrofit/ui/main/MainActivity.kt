package es.javiercarrasco.myretrofit.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.javiercarrasco.myretrofit.R
import es.javiercarrasco.myretrofit.adapters.ProductsRecyclerAdapter
import es.javiercarrasco.myretrofit.data.StoreDataSource
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.databinding.ActivityMainBinding
import es.javiercarrasco.myretrofit.ui.detail.DetailActivity
import kotlinx.coroutines.flow.catch
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
            DetailActivity.navigateToDetail(this@MainActivity, prodId = product.id)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding.textView.text = getString(R.string.txtCargando)
        binding.recyclerProducts.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerProducts.adapter = adapter

        collectCategories()

        collectProducts()
    }

    override fun onStart() {
        super.onStart()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            adapter.submitList(emptyList())
            when (item.itemId) {
                R.id.itemBottom1 -> { // All
                    vm.fetchProducts()
                }
                else -> {
                    vm.fetchProductsByCategory(item.title.toString())
                }
            }
            collectProducts()
            true
        }
    }

    private fun collectProducts() {
        lifecycleScope.launch {
            vm._products
                .catch {
                    println("ERROR: ${it}")
                }
                .collect {
                    it.forEach {
                        println("PRODUCTO: ${it.title}")
                    }
                    adapter.submitList(it)
                }
        }
    }

    private fun collectCategories() {
        lifecycleScope.launch {
            vm.categories
                .catch {
                    println("ERROR: ${it}")
                }
                .collect {
                    binding.bottomNavigation.menu.apply {
                        this.findItem(R.id.itemBottom2).title = it[0]
                        this.findItem(R.id.itemBottom3).title = it[1]
                        this.findItem(R.id.itemBottom4).title = it[2]
                        this.findItem(R.id.itemBottom5).title = it[3]
                    }
                }
        }
    }
}