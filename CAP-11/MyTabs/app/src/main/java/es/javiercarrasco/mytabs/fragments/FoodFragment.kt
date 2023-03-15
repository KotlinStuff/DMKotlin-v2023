package es.javiercarrasco.mytabs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.javiercarrasco.mytabs.databinding.FragmentAnimalsBinding
import es.javiercarrasco.mytabs.databinding.FragmentFoodBinding

class FoodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFoodBinding.inflate(inflater, container, false).root
    }
}