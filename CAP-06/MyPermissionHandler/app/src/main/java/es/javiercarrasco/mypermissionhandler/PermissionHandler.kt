package es.javiercarrasco.mypermissionhandler

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat

class PermissionHandler(val activity: Activity) {

    fun checkPermission(permission: String): Boolean {
        return when {
            ContextCompat.checkSelfPermission(
                activity, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permiso ya concedido.
                true
            }

            // Este método devuelve TRUE si se ha denegado el permiso en algún momento.
            activity.shouldShowRequestPermissionRationale(permission) -> {
                // Se muestra una explicación sobre la necesidad de conceder el permiso.
                // En este caso se muestra un Toast, pero lo ideal sería mediante un Dialog,
                // de tal forma que se pueda controlar que si acepta, se vuelva a pedir y si cancela,
                // continúe la aplicación sin bloquearla, pero sin acceso al recurso.
                Toast.makeText(
                    activity,
                    activity.getString(R.string.showInContextUI),
                    Toast.LENGTH_LONG
                ).show()
                false
            }
            else -> {
                // You can directly ask for the permission.
                // Se recoge la respuesta del usuario mediante el callback creado.
                false
            }
        }
    }
}