package es.javiercarrasco.myretrofit.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import es.javiercarrasco.myretrofit.R
import es.javiercarrasco.myretrofit.ShareApp
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products
import es.javiercarrasco.myretrofit.utils.checkConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel(private val storeRepository: StoreRepository) : ViewModel() {
    private var _products: Flow<List<Products>> = storeRepository.fetchProducts()
    val products: Flow<List<Products>>
        get() = _products

    var categories: Flow<List<String>> = storeRepository.fetchCategories()

    private var _token: MutableStateFlow<Login> = MutableStateFlow(Login())
    val token: StateFlow<Login> = _token.asStateFlow()

    fun fetchProducts() { // Método utilizado para recuperar todos los productos
        _products = storeRepository.fetchProducts()
    }

    fun fetchProductsByCategory(category: String) {
        _products = storeRepository.fetchProductsByCategory(category)
    }

    fun getLogin(context: Context, user: String, pass: String) {
        if (checkConnection(context)) {
            viewModelScope.launch {
                try {
                    _token.value = storeRepository.login(user, pass)
                }catch (e: retrofit2.HttpException) {
                    _token.value = Login()
                }
            }
        } else {
            _token.value = Login()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val storeRepository: StoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(storeRepository) as T
    }
}