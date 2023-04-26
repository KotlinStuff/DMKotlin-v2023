package es.javiercarrasco.myroom.data

import es.javiercarrasco.myroom.domain.Editorial
import es.javiercarrasco.myroom.domain.SuperHero
import es.javiercarrasco.myroom.domain.SupersWithEditorial
import kotlinx.coroutines.flow.Flow

class SupersRepository(private val supersRoomDataSource: SupersDataSource) {
    val currentSupers: Flow<List<SupersWithEditorial>> = supersRoomDataSource.currentSupers
    val currentEditorials: Flow<List<Editorial>> = supersRoomDataSource.currentEditorials

    suspend fun deleteSuper(superHero: SuperHero) {
        supersRoomDataSource.deleteSuper(superHero)
    }

    suspend fun saveSuper(superHero: SuperHero) {
        supersRoomDataSource.saveSuper(superHero)
    }

    suspend fun getSuperById(superId: Int): SuperHero? =
        supersRoomDataSource.getSuperById(superId)

    suspend fun deleteEditorial(editorial: Editorial) {
        supersRoomDataSource.deleteEditorial(editorial)
    }

    suspend fun saveEditorial(editorial: Editorial) {
        supersRoomDataSource.saveEditorial(editorial)
    }

    suspend fun getEditorialById(editorialId: Int): Editorial? =
        supersRoomDataSource.getEditorialById(editorialId)
}