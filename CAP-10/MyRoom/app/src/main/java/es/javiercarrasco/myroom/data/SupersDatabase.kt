package es.javiercarrasco.myroom.data

import android.database.Cursor
import androidx.room.*
import es.javiercarrasco.myroom.data.model.Editorial
import es.javiercarrasco.myroom.data.model.SuperHero
import es.javiercarrasco.myroom.data.model.SupersWithEditorial

@Database(entities = [SuperHero::class, Editorial::class], version = 1, exportSchema = false)
abstract class SupersDatabase : RoomDatabase() {
    abstract fun supersDAO(): SupersDAO
}

@Dao
interface SupersDAO {

    @Query(
        "SELECT * FROM SuperHero " +
                "INNER JOIN Editorial ON idEditorial = idEd ORDER BY superName"
    )
    suspend fun getSuperHerosWithEditorials(): Map<SuperHero, Editorial>

    @Query("SELECT * FROM SuperHero ORDER BY superName")
    suspend fun getAllSuperHeros(): MutableList<SuperHero>

    @Transaction
    @Query("SELECT * FROM SuperHero ORDER BY superName")
    suspend fun getAllSuperHerosWithEditorials(): MutableList<SupersWithEditorial>

    @Query("SELECT * FROM SuperHero WHERE idSuper = :idSuper")
    suspend fun getSuperById(idSuper: Int): SuperHero?

    @Query("SELECT * FROM Editorial")
    suspend fun getAllEditorials(): List<Editorial>

    @Query("SELECT idEd as _id, name FROM Editorial")
    fun getAllEditorials2(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEditorial(editorial: Editorial)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuperHero(superHero: SuperHero)

    @Delete
    suspend fun deleteEditorial(editorial: Editorial)

    @Delete
    suspend fun deleteSuperHero(superHero: SuperHero)

    @Update
    suspend fun updateEditorial(editorial: Editorial)

    @Update
    suspend fun updateSuperHero(superHero: SuperHero)
}