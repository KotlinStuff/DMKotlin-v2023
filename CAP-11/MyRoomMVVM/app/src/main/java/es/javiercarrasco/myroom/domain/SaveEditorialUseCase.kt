package es.javiercarrasco.myroom.domain

import es.javiercarrasco.myroom.data.SupersRepository
import es.javiercarrasco.myroom.domain.model.Editorial

class SaveEditorialUseCase constructor(private val supersRepository: SupersRepository) {
    // se utiliza la palabra reservada operator para poder hacer una llamda directa.
    suspend operator fun invoke(editorial: Editorial) {
        supersRepository.saveEditorial(editorial)
    }
}