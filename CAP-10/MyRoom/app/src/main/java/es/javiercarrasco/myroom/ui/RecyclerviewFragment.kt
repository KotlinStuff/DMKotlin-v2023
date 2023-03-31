package es.javiercarrasco.myroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.javiercarrasco.myroom.adapters.SupersRecyclerAdapter
import es.javiercarrasco.myroom.databinding.FragmentRecyclerviewBinding

class RecyclerviewFragment(private val db: SupersDBHelper) : Fragment() {
    private lateinit var binding: FragmentRecyclerviewBinding
    private lateinit var adapter: SupersRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SupersRecyclerAdapter(
            onSuperHeroClick = {
                SuperheroActivity.navigate(
                    (requireActivity() as AppCompatActivity),
                    it.id
                )
            },
            onSuperHeroLongClick = {
                if (db.delSuperHero(it.id) != 0)
                    adapter.submitList(db.getAllSuperHeros())
            },
            onFabClick = {
                db.updateFab(it.id, it.favorite)
                adapter.submitList(db.getAllSuperHeros())
            }
        )

        binding.recycler.adapter = adapter

        adapter.submitList(db.getAllSuperHeros())

//        for ((i, superHero) in supersList.withIndex()) {
//            Log.d("$i", "${superHero.superName}")
//        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(db.getAllSuperHeros())
    }
}