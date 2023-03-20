package es.javiercarrasco.mynavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.mynavigation.databinding.FragmentTresBinding

class TresFragment : Fragment() {
    private lateinit var binding: FragmentTresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTresBinding.inflate(inflater, container, false)

        binding.textView.text = TresFragmentArgs.fromBundle(
            requireArguments()
        ).numFragment

        binding.button.setOnClickListener {
            findNavController().navigate(TresFragmentDirections.actionTresFragmentToUnoFragment(
                getString(R.string.txt_fragment, "UNO")
            ))
        }

        return binding.root
    }
}