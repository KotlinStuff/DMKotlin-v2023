package es.javiercarrasco.mysqlite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.javiercarrasco.mysqlite.databinding.FragmentListviewBinding
import es.javiercarrasco.mysqlite.databinding.FragmentRecyclerviewBinding

class RecyclerviewFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

}