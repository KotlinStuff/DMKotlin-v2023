package es.javiercarrasco.myroom.data

import android.database.Cursor
import androidx.room.*
import es.javiercarrasco.myroom.data.model.*

@Database(
    entities = [
        SuperHero::class,
        Editorial::class,
        Illustrator::class,
        EditorialsIllustrators::class
    ],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
    //exportSchema = false
)
abstract class SupersDatabase : RoomDatabase() {
    abstract fun supersDAO(): SupersDAO
}

@Dao
interface SupersDAO {

    @Transaction
    @Query("SELECT * FROM Editorial")
    suspend fun getAllEditorialsWithIllustrators(): MutableList<EditorialsWithIllustrators>

    @Transaction
    @Query("SELECT * FROM Illustrator")
    suspend fun getAllIllustratorsWithEditorials(): MutableList<IllustratrosWithEditorials>

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

    @Transaction
    @Query("SELECT * FROM Editorial ORDER BY name")
    suspend fun getAllEditorialWithSupers(): MutableList<EditorialWithSupers>

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIllustrator(illustrator: Illustrator)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEditorialsIllustrators(editorialsIllustrators: EditorialsIllustrators)

    @Delete
    suspend fun deleteEditorial(editorial: Editorial)

    @Delete
    suspend fun deleteSuperHero(superHero: SuperHero)

    @Update
    suspend fun updateEditorial(editorial: Editorial)

    @Update
    suspend fun updateSuperHero(superHero: SuperHero)
}