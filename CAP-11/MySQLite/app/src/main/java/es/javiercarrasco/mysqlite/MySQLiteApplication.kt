package es.javiercarrasco.mysqlite

import android.app.Application
import es.javiercarrasco.mysqlite.data.SupersDBHelper

// Añadir la propiedad name de application al Manifest.
class MySQLiteApplication : Application() {
    lateinit var supersDBHelper: SupersDBHelper
        private set

    override fun onCreate() {
        super.onCreate()

        supersDBHelper = SupersDBHelper(this, null)
    }
}