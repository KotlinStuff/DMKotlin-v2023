package es.javiercarrasco.mynavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.mynavigation.databinding.FragmentDosBinding

class DosFragment : Fragment() {
    private lateinit var binding: FragmentDosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDosBinding.inflate(inflater, container, false)

        binding.textView.text = DosFragmentArgs.fromBundle(
            requireArguments()
        ).numFragment

        binding.button.setOnClickListener {
            findNavController().navigate(DosFragmentDirections.actionDosFragmentToTresFragment(
                getString(R.string.txt_fragment, "TRES")
            ))
        }

        return binding.root
    }
}