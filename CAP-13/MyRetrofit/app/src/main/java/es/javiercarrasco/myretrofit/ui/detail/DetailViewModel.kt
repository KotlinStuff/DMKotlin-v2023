package es.javiercarrasco.myretrofit.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val storeRepository: StoreRepository, private val prodId: Int) :
    ViewModel() {
    private val _state = MutableStateFlow(Products())
    val state: StateFlow<Products> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val product = storeRepository.fetchProductById(prodId)
            if (product != null)
                _state.value = product
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val storeRepository: StoreRepository,
    private val prodId: Int
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(storeRepository, prodId) as T
    }
}