package es.javiercarrasco.myretrofit.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products
import es.javiercarrasco.myretrofit.utils.checkConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val storeRepository: StoreRepository) : ViewModel() {
    var products = MutableLiveData<List<Products>>()
    var categories = MutableLiveData<List<String>>()

    private var _token: MutableStateFlow<Login> = MutableStateFlow(Login())
    val token: StateFlow<Login> = _token.asStateFlow()

    init {
        fetchProducts()
        fetchCategories()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            val call = storeRepository.fetchProducts()
            if (call.isSuccessful)
                products.postValue(call.body())
            else products.postValue(emptyList())
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            val call = storeRepository.fetchCategories()
            if (call.isSuccessful)
                categories.postValue(call.body())
            else categories.postValue(emptyList())
        }
    }

    fun fetchProductsByCategory(category: String) {
        viewModelScope.launch {
            val call = storeRepository.fetchProductsByCategory(category)
            if (call.isSuccessful)
                products.postValue(call.body())
            else products.postValue(emptyList())
        }
    }

    fun getLogin(context: Context, user: String, pass: String) {
        if (checkConnection(context)) {
            viewModelScope.launch {
                try {
                    _token.value = storeRepository.login(user, pass)
                } catch (e: retrofit2.HttpException) {
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