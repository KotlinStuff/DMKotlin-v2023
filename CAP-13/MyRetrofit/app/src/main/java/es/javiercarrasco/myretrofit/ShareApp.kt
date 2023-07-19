package es.javiercarrasco.myretrofit

import android.app.Application
import es.javiercarrasco.myretrofit.domain.model.Login

class ShareApp : Application() {
    private val PREFS_NAME = "es.javiercarrasco.myretrofit"
    private val SHARED_NAME = "token"

    companion object {
        lateinit var preferences: Preferences
            private set
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences()
    }

    inner class Preferences() {
        val prefs = applicationContext.getSharedPreferences(
            PREFS_NAME,
            MODE_PRIVATE
        )

        var token: String
            get() = prefs.getString(SHARED_NAME, Login().token) ?: Login().token
            set(value) = prefs.edit().putString(SHARED_NAME, value).apply()
    }
}
