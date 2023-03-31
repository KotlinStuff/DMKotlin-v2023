package es.javiercarrasco.myroom

import android.app.Application

// Añadir la propiedad name de application al Manifest.
class MySQLiteApplication : Application() {
    lateinit var supersDBHelper: SupersDBHelper
        private set

    override fun onCreate() {
        super.onCreate()

        supersDBHelper = SupersDBHelper(this, null)
    }
}