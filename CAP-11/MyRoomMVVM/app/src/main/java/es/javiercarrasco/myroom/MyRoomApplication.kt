package es.javiercarrasco.myroom

import android.app.Application
import androidx.room.Room
import es.javiercarrasco.myroom.data.SupersDatabase

// Añadir la propiedad name de application al Manifest.
class MyRoomApplication : Application() {
    lateinit var supersDatabase: SupersDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        supersDatabase = Room.databaseBuilder(
            this, SupersDatabase::class.java, "SuperHeros.db"
        ).build()
    }
}