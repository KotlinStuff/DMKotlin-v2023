package es.javiercarrasco.myroom.data

import es.javiercarrasco.myroom.domain.model.Editorial
import es.javiercarrasco.myroom.domain.model.SuperHero
import es.javiercarrasco.myroom.domain.model.SupersWithEditorial
import kotlinx.coroutines.flow.Flow

class SupersDataSource(private val db: SupersDAO) {
    val currentSupers: Flow<List<SupersWithEditorial>> = db.getSuperHerosWithEditorials()
    val currentEditorials: Flow<List<Editorial>> = db.getAllEditorials()
    val currentNumEditorials: Flow<Int> = db.getNumEditorials()

    suspend fun deleteSuper(superHero: SuperHero) {
        db.deleteSuperHero(superHero)
    }

    suspend fun saveSuper(superHero: SuperHero) {
        db.insertSuperHero(superHero)
    }

    suspend fun getSuperById(superId: Int): SuperHero? = db.getSuperById(superId)

    suspend fun deleteEditorial(editorial: Editorial) {
        db.deleteEditorial(editorial)
    }

    suspend fun saveEditorial(editorial: Editorial) {
        db.insertEditorial(editorial)
    }

    suspend fun getEdById(editorialId: Int): Editorial? = db.getEditorialById(editorialId)
}