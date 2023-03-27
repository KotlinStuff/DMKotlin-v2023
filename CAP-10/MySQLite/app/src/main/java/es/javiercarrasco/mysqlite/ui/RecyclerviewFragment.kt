package es.javiercarrasco.mysqlite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.mysqlite.MySQLiteApplication
import es.javiercarrasco.mysqlite.adapters.SupersRecyclerAdapter
import es.javiercarrasco.mysqlite.data.SupersDBHelper
import es.javiercarrasco.mysqlite.databinding.FragmentRecyclerviewBinding

class RecyclerviewFragment(private val db: SupersDBHelper) : Fragment() {
    private lateinit var binding: FragmentRecyclerviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supersList = db.getAllSuperHeros()

        val adapter = SupersRecyclerAdapter()

        binding.recycler.adapter = adapter

        adapter.submitList(supersList)

//        for ((i, superHero) in supersList.withIndex()) {
//            Log.d("$i", "${superHero.superName}")
//        }
    }
}