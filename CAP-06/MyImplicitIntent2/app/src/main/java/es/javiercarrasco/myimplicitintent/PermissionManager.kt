package es.javiercarrasco.myimplicitintent

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PermissionManager(
    val activity: Activity,
    val permiso: String,
    val code: Int
) {

    fun checkPermissions(): Boolean {
        // Se comprueba si el permiso en cuestión está concedido.
        if (ContextCompat.checkSelfPermission(
                activity, permiso
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no está concedido el permiso se entra.
            Log.d("DEBUG", "No tienes permiso para esta acción: $permiso")

            // Si el usuario ya lo ha rechazado al menos una vez (TRUE),
            // se puede mostrar una explicación.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, permiso
                )
            ) {
                Log.d("DEBUG", "Se da una explicación")
                ShowAdditionalInformation()
            } else {
                // No requiere explicación, se pregunta por el permiso.
                Log.d("DEBUG", "No se da una explicación.")
                ActivityCompat.requestPermissions(
                    activity, arrayOf(permiso), code
                )
            }
        } else Log.d("DEBUG", "Permiso ($permiso) concedido!")

        return ContextCompat.checkSelfPermission(
            activity, permiso
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun ShowAdditionalInformation() {
        MaterialAlertDialogBuilder(activity)
            .setTitle("Concesión de permisos")
            .setMessage(
                "Puede resultar interesante indicar" +
                        " porqué se necesita conceder permiso."
            )
            .setPositiveButton(android.R.string.ok) { _, _ ->
                Log.d("DEBUG", "Se acepta y se vuelve a pedir permiso")
                ActivityCompat.requestPermissions(
                    activity, arrayOf(permiso), code
                )
            }
            .setNeutralButton(android.R.string.cancel, null)
            .show()
    }
}