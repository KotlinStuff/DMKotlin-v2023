package es.javiercarrasco.myokhttp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.javiercarrasco.myokhttp.adapters.WordsRecyclerAdapter
import es.javiercarrasco.myokhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    private val adapter: WordsRecyclerAdapter by lazy {
        WordsRecyclerAdapter(onClickWords = { word ->
            MaterialAlertDialogBuilder(this).apply {
                setTitle(word.palabra)
                setMessage(word.definicion)
                setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerWords.adapter = adapter

        if (checkConnection(this)) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    vm.wordsList.collect {
                        adapter.submitList(it)
                    }
                }
            }
        } else {
            binding.recyclerWords.visibility = View.GONE

            val aviso = TextView(this)
            aviso.text = getString(R.string.NoConnection)
            binding.root.addView(aviso)
        }
    }
}