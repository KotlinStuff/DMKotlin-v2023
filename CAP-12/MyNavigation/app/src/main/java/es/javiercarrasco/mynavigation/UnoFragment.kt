package es.javiercarrasco.mynavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.mynavigation.databinding.FragmentUnoBinding

class UnoFragment : Fragment() {
    private lateinit var binding: FragmentUnoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUnoBinding.inflate(inflater, container, false)

        if (UnoFragmentArgs.fromBundle(requireArguments()).numFragment != "nulo")
            binding.textView.text = UnoFragmentArgs.fromBundle(
                requireArguments()
            ).numFragment
        else binding.textView.text = "INICIO"

        binding.button.setOnClickListener {
            findNavController().navigate(
                UnoFragmentDirections.actionUnoFragmentToDosFragment(
                    getString(R.string.txt_fragment, "DOS")
                )
            )
        }

        return binding.root
    }
}