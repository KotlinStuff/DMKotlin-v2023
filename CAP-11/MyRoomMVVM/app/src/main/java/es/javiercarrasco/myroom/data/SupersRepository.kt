package es.javiercarrasco.myroom.data

import es.javiercarrasco.myroom.domain.model.Editorial
import es.javiercarrasco.myroom.domain.model.SuperHero
import es.javiercarrasco.myroom.domain.model.SupersWithEditorial
import kotlinx.coroutines.flow.Flow

class SupersRepository(private val supersRoomDataSource: SupersDataSource) {
    val currentSupers: Flow<List<SupersWithEditorial>> = supersRoomDataSource.currentSupers
    val currentEditorials: Flow<List<Editorial>> = supersRoomDataSource.currentEditorials
    val currentNumEditorials: Flow<Int> = supersRoomDataSource.currentNumEditorials

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
        supersRoomDataSource.getEdById(editorialId)
}