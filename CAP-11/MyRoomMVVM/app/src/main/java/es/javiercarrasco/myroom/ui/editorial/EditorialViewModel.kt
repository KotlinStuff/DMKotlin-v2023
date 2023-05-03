package es.javiercarrasco.myroom.ui.editorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.domain.SaveEditorialUseCase
import es.javiercarrasco.myroom.domain.model.Editorial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditorialViewModel(
    private val saveEditorialUseCase: SaveEditorialUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(Editorial())
    val state = _state.asStateFlow() // No se está utilizando porque no se recupera el valor.

    fun save(name: String) {
        viewModelScope.launch {
            val editorial = _state.value.copy(
                name = name
            )
            saveEditorialUseCase(editorial)
        }
    }
}

// El Factory se utiliza para crear el ViewModel e indicar los parámetros a pasarle.
// Si no tiene parámetros no sería necesario crearlo.
@Suppress("UNCHECKED_CAST")
class EditorialViewModelFactory(
    private val saveEditorialUseCase: SaveEditorialUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditorialViewModel(saveEditorialUseCase) as T
    }
}