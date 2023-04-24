package es.javiercarrasco.myroom.data

import es.javiercarrasco.myroom.model.Editorial
import es.javiercarrasco.myroom.model.SuperHero
import es.javiercarrasco.myroom.model.SupersWithEditorial
import kotlinx.coroutines.flow.Flow

class SupersRoomDataSource(private val db: SupersDAO) {
    val currentSupers: Flow<List<SupersWithEditorial>> = db.getAllSuperHerosWithEditorials()

    suspend fun delete(superHero: SuperHero) {
        db.deleteSuperHero(superHero)
    }

    suspend fun save(superHero: SuperHero) {
        db.insertSuperHero(superHero)
    }

    suspend fun getById(superId: Int): SuperHero? = db.getSuperById(superId)
}

class EditorialsRoomDataSource(private val db: SupersDAO) {
    val currentEditorials: Flow<List<Editorial>> = db.getAllEditorials()

    suspend fun delete(editorial: Editorial) {
        db.deleteEditorial(editorial)
    }

    suspend fun save(editorial: Editorial) {
        db.insertEditorial(editorial)
    }

    suspend fun getById(editorialId: Int): Editorial? = db.getEditorialById(editorialId)
}