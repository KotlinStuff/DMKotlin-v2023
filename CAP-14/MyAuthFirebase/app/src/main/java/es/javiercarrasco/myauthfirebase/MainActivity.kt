package es.javiercarrasco.myauthfirebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.javiercarrasco.myauthfirebase.databinding.ActivityMainBinding
import es.javiercarrasco.myauthfirebase.databinding.LoginLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private val TAG = "FirebaseAuth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        // Si el usuario existe NO será nulo.
        updateUI(auth.currentUser)

        binding.btnLogin.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                updateUI(null)
            } else showLoginDialog(R.string.txt_btn_login)
        }

        binding.btnRegister.setOnClickListener {
            if (auth.currentUser != null)
                sendEmailVerification()
            else showLoginDialog(R.string.txt_btn_register)
        }
    }

    // Método para comprobar el estado del usuario actual,
    // si está registrado su valor no será nulo.
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.tvInfo.text = getString(R.string.txt_user_login)
            binding.tvInfo.append(
                getString(
                    R.string.txt_info_user,
                    user.email,
                    user.isEmailVerified,
                    user.uid
                )
            )
            binding.btnLogin.text = getString(R.string.txt_btn_logout)

            // Se habilita el botón de verificación si el email está no verificado.
            binding.btnRegister.isEnabled = !user.isEmailVerified
            binding.btnRegister.text = getString(R.string.txt_btn_verify_email)
        } else {
            binding.tvInfo.text = getString(R.string.txt_user_no_login)
            binding.btnLogin.text = getString(R.string.txt_btn_login)
            binding.btnRegister.text = getString(R.string.txt_btn_register)
        }
    }

    private fun showLoginDialog(title: Int) {
        val bindCustomDialog = LoginLayoutBinding.inflate(layoutInflater)

        val dialogLogin = MaterialAlertDialogBuilder(this).apply {
            setView(bindCustomDialog.root)
            setTitle(title)
            setPositiveButton(android.R.string.ok, null)
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
        }.create()

        dialogLogin.setOnShowListener {
            dialogLogin.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val email = bindCustomDialog.etEmail.text.toString().trim()
                val pass = bindCustomDialog.etPassword.text.toString().trim()

                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    if (title == R.string.txt_btn_register)
                        createAccount(email, pass)

                    dialogLogin.dismiss()
                } else {
                    bindCustomDialog.etEmail.error = getString(R.string.txtErrorDialog)
                    bindCustomDialog.etPassword.error = getString(R.string.txtErrorDialog)
                }
            }
        }

        dialogLogin.show()
    }

    // Método encargado de crear la cuenta de usuario mediante email y password.
    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Alta correcta, se actualiza la UI con la nueva información.
                    Log.d(TAG, "createUserWithEmail:success")
                    updateUI(auth.currentUser)
                } else {
                    // Fallo, se muestra mensaje al usuario.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    // Método encargado de enviar el email de verificación al usuario.
    private fun sendEmailVerification() {
        // Deshabilita el botón de verificación.
        binding.btnRegister.isEnabled = false

        // Envío email verificación
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                // Se habilita el botón
                binding.btnRegister.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Email de verificación enviado a ${user.email} ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Fallo en el envío del email de verifiación.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}