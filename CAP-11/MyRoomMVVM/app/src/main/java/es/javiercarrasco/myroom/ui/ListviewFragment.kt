package es.javiercarrasco.myroom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import es.javiercarrasco.myroom.data.SupersDatabase
import es.javiercarrasco.myroom.data.model.SuperHero
import es.javiercarrasco.myroom.databinding.FragmentListviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListviewFragment(private val db: SupersDatabase) : Fragment() {
    private lateinit var binding: FragmentListviewBinding
    private lateinit var mContext: Context

    // Se evitan problemas de contexto no adjuntado.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val supersList: MutableList<SuperHero>

            withContext(Dispatchers.IO) {
                supersList = db.supersDAO().getAllSuperHeros()
            }

            // Se crea el ArrayList con el HashMap de los superhéroes.
            val supersHashMap: ArrayList<HashMap<String, String>> = ArrayList()
            supersList.forEach {
                val map: HashMap<String, String> = HashMap()
                map.put("superName", it.superName!!)
                map.put("realName", it.realName!!)
                supersHashMap.add(map)
            }.run {

                val adapter = SimpleAdapter(
                    mContext,
                    supersHashMap,
                    android.R.layout.simple_list_item_2,
                    arrayOf("superName", "realName"),
                    intArrayOf(android.R.id.text1, android.R.id.text2)
                )

                binding.listView.adapter = adapter
            }
        }
    }
}