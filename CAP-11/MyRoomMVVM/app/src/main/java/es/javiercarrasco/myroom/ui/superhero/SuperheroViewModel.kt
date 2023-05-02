package es.javiercarrasco.myroom.ui.superhero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.domain.SuperHero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SuperheroViewModel(
    private val supersRepository: SupersRepository,
    private val superId: Int
) : ViewModel() {

    private val _stateSuper = MutableStateFlow(SuperHero(0))
    val stateSuper = _stateSuper.asStateFlow()

    val stateEd = supersRepository.currentEditorials

    // Se intenta recuperar el superhéroe que se pasa por id.
    init {
        viewModelScope.launch {
            val superhero = supersRepository.getSuperById(superId)
            if (superhero != null)
                _stateSuper.value = superhero
        }
    }

    fun save(superhero: SuperHero) {
        viewModelScope.launch {
            val superheroAux = _stateSuper.value.copy(
                superName = superhero.superName,
                realName = superhero.realName,
                favorite = superhero.favorite,
                idEditorial = superhero.idEditorial
            )
            supersRepository.saveSuper(superheroAux)
        }
    }
}

// Si no tiene parámetros no sería necesario crearlo.
@Suppress("UNCHECKED_CAST")
class SuperheroViewModelFactory(
    private val supersRepository: SupersRepository,
    private val superId: Int
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SuperheroViewModel(supersRepository, superId) as T
    }
}