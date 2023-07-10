package es.javiercarrasco.myretrofit.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MainViewModel(private val storeRepository: StoreRepository) : ViewModel() {
    private var products: Flow<List<Products>> = emptyFlow()
    val _products: Flow<List<Products>>
        get() = products

    val categories: Flow<List<String>> = storeRepository.fetchCategories()

    init {
        fetchProducts()
    }
    fun fetchProducts() {
        products = storeRepository.fetchProducts()
    }

    fun fetchProductsByCategory(category: String) {
        products = storeRepository.fetchProductsByCategory(category)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val storeRepository: StoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(storeRepository) as T
    }
}