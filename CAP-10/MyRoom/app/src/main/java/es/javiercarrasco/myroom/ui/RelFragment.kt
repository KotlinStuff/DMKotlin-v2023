package es.javiercarrasco.myroom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.data.model.Editorial
import es.javiercarrasco.myroom.data.model.SuperHero
import es.javiercarrasco.myroom.databinding.FragmentListviewBinding
import es.javiercarrasco.myroom.databinding.FragmentRelBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RelFragment(private val db: SupersDatabase) : Fragment() {
    private lateinit var binding: FragmentRelBinding
    private lateinit var mContext: Context

    // Se evitan problemas de contexto no adjuntado.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            binding.textView.setText("")

            withContext(Dispatchers.IO) {
                db.supersDAO().getSuperHerosWithEditorials()
            }.run {
                this.forEach {
                    binding.textView.append("${it}\n\n")
                }
            }
        }
    }
}