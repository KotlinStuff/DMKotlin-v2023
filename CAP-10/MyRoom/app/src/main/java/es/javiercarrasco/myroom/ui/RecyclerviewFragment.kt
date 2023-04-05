package es.javiercarrasco.myroom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.javiercarrasco.myroom.adapters.SupersRecyclerAdapter
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.databinding.FragmentRecyclerviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerviewFragment(private val db: SupersDatabase) : Fragment() {
    private lateinit var binding: FragmentRecyclerviewBinding
    private lateinit var adapter: SupersRecyclerAdapter
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

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
                    it.idSuper
                )
            },
            onSuperHeroLongClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    db.supersDAO().deleteSuperHero(it)
                    adapter.submitList(db.supersDAO().getAllSuperHerosWithEditorials())
                }
            },
            onFabClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val updated = it.copy(
                        favorite = if (it.favorite == 0) 1 else 0
                    )
                    db.supersDAO().insertSuperHero(updated)
                    adapter.submitList(db.supersDAO().getAllSuperHerosWithEditorials())
                }
            }
        )

        binding.recycler.adapter = adapter

        updateRecycler()

//        for ((i, superHero) in supersList.withIndex()) {
//            Log.d("$i", "${superHero.superName}")
//        }
    }

    private fun updateRecycler() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                db.supersDAO().getAllSuperHerosWithEditorials()
            }.apply {
                adapter.submitList(this)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        updateRecycler()
    }
}