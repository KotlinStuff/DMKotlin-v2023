package es.javiercarrasco.mynavigationdrawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.mynavigationdrawer.databinding.FragmentPageBinding

private const val ARG_PARAM1 = "titulo"
private const val ARG_PARAM2 = "subtitulo"

class PageFragment : Fragment() {
    private lateinit var binding: FragmentPageBinding
    private var titulo: String? = null
    private var subtitulo: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titulo = it.getString(ARG_PARAM1)
            subtitulo = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textView.text = titulo
        binding.textView2.text = subtitulo
        Log.d("FRAGMENT", "Creado!!")
    }
}