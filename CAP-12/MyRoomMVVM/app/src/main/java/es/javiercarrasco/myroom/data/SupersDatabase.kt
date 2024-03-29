package es.javiercarrasco.myroom.data

import androidx.room.*
import es.javiercarrasco.myroom.domain.model.Editorial
import es.javiercarrasco.myroom.domain.model.SuperHero
import es.javiercarrasco.myroom.domain.model.SupersWithEditorial
import kotlinx.coroutines.flow.Flow

@Database(entities = [SuperHero::class, Editorial::class], version = 1)
abstract class SupersDatabase : RoomDatabase() {
    abstract fun supersDAO(): SupersDAO
}

@Dao
interface SupersDAO {
    @Transaction
    @Query("SELECT * FROM SuperHero ORDER BY superName")
    fun getSuperHerosWithEditorials(): Flow<List<SupersWithEditorial>>

    @Query("SELECT * FROM SuperHero WHERE idSuper = :idSuper")
    suspend fun getSuperById(idSuper: Int): SuperHero?

    @Query("SELECT * FROM Editorial")
    fun getAllEditorials(): Flow<List<Editorial>>

    @Query("SELECT count(idEd) FROM Editorial")
    fun getNumEditorials(): Flow<Int>

    @Query("SELECT * FROM Editorial WHERE idEd = :editorialId")
    suspend fun getEditorialById(editorialId: Int): Editorial?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEditorial(editorial: Editorial)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuperHero(superHero: SuperHero)

    @Delete
    suspend fun deleteEditorial(editorial: Editorial)

    @Delete
    suspend fun deleteSuperHero(superHero: SuperHero)
}