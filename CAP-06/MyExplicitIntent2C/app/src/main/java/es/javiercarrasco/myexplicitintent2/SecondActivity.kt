package es.javiercarrasco.myexplicitintent2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myexplicitintent2.MainActivity.Companion.TAG_APP
import es.javiercarrasco.myexplicitintent2.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se recuperan los datos y se asignan al TextView.
        intent.getStringExtra(MainActivity.EXTRA_NAME).apply {
            binding.tvNameReceived.text = getString(
                R.string.msgAccept,
                this
            )

            binding.btAccept.setOnClickListener {
                val intentResult: Intent = Intent().apply {
                    // Se añade el valor del rating.
                    putExtra("RATING", binding.ratingBar.rating)
                }

                setResult(Activity.RESULT_OK, intentResult)
                Log.d(TAG_APP, "Valor devuelto OK y valoración RatingBar")
                finish()
            }

            binding.btCancel.setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                Log.d(TAG_APP, "Valor devuelto CANCELED")
                finish()
            }
        }
    }

    override fun onBackPressed() {
        Toast.makeText(
            applicationContext,
            "onBackPressed() called!!",
            Toast.LENGTH_SHORT
        ).show()
        //super.onBackPressed()
    }
}