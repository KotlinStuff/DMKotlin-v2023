package es.javiercarrasco.myroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.domain.SuperHero
import kotlinx.coroutines.launch

class MainViewModel(private val supersRepository: SupersRepository) : ViewModel() {
    val state = supersRepository.currentSupers

    fun onSuperDelete(superHero: SuperHero) {
        viewModelScope.launch {
            supersRepository.deleteSuper(superHero)
        }
    }

    fun onFabSuper(superHero: SuperHero) {
        viewModelScope.launch {
            val superheroAux = superHero.copy(
                favorite = if (superHero.favorite == 0) 1 else 0
            )
            supersRepository.saveSuper(superheroAux)
        }
    }
}

// Si no tiene parámetros no sería necesario crearlo.
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val supersRepository: SupersRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(supersRepository) as T
    }
}