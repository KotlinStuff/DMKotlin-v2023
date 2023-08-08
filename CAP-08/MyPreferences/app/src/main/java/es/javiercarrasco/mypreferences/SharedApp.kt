package es.javiercarrasco.mypreferences

import android.app.Application

class SharedApp : Application() {
    companion object {
        lateinit var preferences: Preferences
            private set // El setter será privado y utiliza la implementación por defecto.
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(applicationContext)
    }
}