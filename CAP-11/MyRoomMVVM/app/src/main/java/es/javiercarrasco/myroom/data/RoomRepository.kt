package es.javiercarrasco.myroom.data

import es.javiercarrasco.myroom.model.Editorial
import es.javiercarrasco.myroom.model.SupersWithEditorial
import kotlinx.coroutines.flow.Flow

class SupersRoomRepository(private val supersRoomDataSource: SupersRoomDataSource) {
    val currentSupers: Flow<List<SupersWithEditorial>> = supersRoomDataSource.currentSupers
}

class EditorialsRoomRepository(private val editorialsRoomDataSource: EditorialsRoomDataSource) {
    val currentEditorials: Flow<List<Editorial>> = editorialsRoomDataSource.currentEditorials

    suspend fun delete(editorial: Editorial) {
        editorialsRoomDataSource.delete(editorial)
    }

    suspend fun getById(editorialId: Int): Editorial? =
        editorialsRoomDataSource.getById(editorialId)

    suspend fun save(editorial: Editorial) {
        editorialsRoomDataSource.save(editorial)
    }
}