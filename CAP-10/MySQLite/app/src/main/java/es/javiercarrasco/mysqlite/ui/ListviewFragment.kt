package es.javiercarrasco.mysqlite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import es.javiercarrasco.mysqlite.data.SupersDBHelper
import es.javiercarrasco.mysqlite.databinding.FragmentListviewBinding

class ListviewFragment(private val db: SupersDBHelper) : Fragment() {
    private lateinit var binding: FragmentListviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cursor = db.getAllSuperHerosCursor()
        val adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf(cursor.columnNames[1], cursor.columnNames[2]),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        binding.listView.adapter = adapter
    }
}

//class MyListCursorAdapter(context: Context, cursor: Cursor) :
//    CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER) {
//
//    // "Infla" cada uno de los elementos de la lista.
//    override fun newView(
//        context: Context?,
//        cursor: Cursor?,
//        parent: ViewGroup?
//    ): View = ItemListViewBinding.inflate(layoutInflater, parent, false).root
//
//    // Rellena el ListView.
//    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
//        val bindingItems = ItemListViewBinding.bind(view!!)
//        with(bindingItems) {
//            tvItemNombre.text = cursor!!.getString(1)
//            tvItemApes.text = cursor.getString(2)
//            view.setOnClickListener {
//                Toast.makeText(
//                    this@ListViewActivity,
//                    "${tvItemNombre.text} ${tvItemApes.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//}