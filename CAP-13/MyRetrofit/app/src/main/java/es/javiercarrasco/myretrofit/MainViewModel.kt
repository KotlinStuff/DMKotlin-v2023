package es.javiercarrasco.myretrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val storeRepository: StoreRepository) : ViewModel() {
    var products: Flow<List<Products>> = storeRepository.fetchProducts()
    val _products: Flow<List<Products>> = products
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val storeRepository: StoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(storeRepository) as T
    }
}