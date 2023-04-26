package es.javiercarrasco.myroom.ui.superhero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.javiercarrasco.myroom.data.SupersRepository

class SuperheroViewModel(private val supersRepository: SupersRepository) : ViewModel() {
    val state = supersRepository.currentEditorials

}

// Si no tiene parámetros no sería necesario crearlo.
@Suppress("UNCHECKED_CAST")
class SuperheroViewModelFactory(private val supersRepository: SupersRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SuperheroViewModel(supersRepository) as T
    }
}