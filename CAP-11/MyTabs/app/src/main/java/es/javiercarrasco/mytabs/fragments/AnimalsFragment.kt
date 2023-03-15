package es.javiercarrasco.mytabs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.javiercarrasco.mytabs.databinding.FragmentAnimalsBinding

class AnimalsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAnimalsBinding.inflate(inflater, container, false).root
    }
}