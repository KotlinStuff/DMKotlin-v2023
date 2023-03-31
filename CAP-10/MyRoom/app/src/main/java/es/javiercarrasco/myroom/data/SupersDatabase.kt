package es.javiercarrasco.myroom.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import es.javiercarrasco.myroom.data.model.Editorial
import es.javiercarrasco.myroom.data.model.SuperHero

@Database(entities = [SuperHero::class, Editorial::class], version = 1)
abstract class SupersDatabase : RoomDatabase() {
    abstract fun supersDAO(): SupersDAO
}

@Dao
interface SupersDAO {

}