package es.javiercarrasco.mydynamictabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.mydynamictabs.databinding.FragmentPageBinding

class PageFragment : Fragment() {
    private lateinit var binding: FragmentPageBinding
    private var nombre: String? = null
    private var texto: String? = null

    companion object {
        @JvmStatic
        fun newInstance(name: String, text: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_TEXT, text)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.nombre = it.getString(ARG_NAME)
            this.texto = it.getString(ARG_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameFragment.text = getString(R.string.txt_fragment, nombre)
        binding.txtFragment.text = getString(R.string.txt_to_show, texto)
    }
}