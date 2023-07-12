package es.javiercarrasco.myretrofit.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.domain.model.Products
import es.javiercarrasco.myretrofit.utils.checkConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList

class MainViewModel(private val storeRepository: StoreRepository) : ViewModel() {
    private var _products: Flow<List<Products>> = emptyFlow()
    val products: Flow<List<Products>>
        get() = _products

    val categories: Flow<List<String>> = storeRepository.fetchCategories()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        _products = storeRepository.fetchProducts()
    }

    fun fetchProductsByCategory(category: String) {
        _products = storeRepository.fetchProductsByCategory(category)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val storeRepository: StoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(storeRepository) as T
    }
}