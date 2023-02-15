package es.javiercarrasco.mygridview2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import es.javiercarrasco.mygridview2.databinding.GridviewItemBinding

// Clase ItemAdapter que hereda de la clase abstracta BaseAdapter.
class ItemAdapter(val context: Context, val itemsList: ArrayList<MyItems>) : BaseAdapter() {

    override fun getCount(): Int {
        return itemsList.size
    }

    override fun getItem(p0: Int): Any {
        return itemsList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    // Devuelve la vista cargada de cada elemento al adaptador.
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return if (p1 == null) { // Se comprueba si la vista es nula.
            val item = this.itemsList[p0]
            val layoutInflater = LayoutInflater.from(context)
            val binding = GridviewItemBinding.inflate(layoutInflater, p2, false)

            binding.image.setImageResource(item.image)
            binding.tvName.text = item.name

            // Pulsación sobre la vista.
//        binding.root.setOnClickListener {
//            Toast.makeText(
//                context,
//                "${binding.tvName.text}",
//                Toast.LENGTH_LONG
//            ).show()
//        }

            binding.root
        } else { // La vista que llena no es nula y se devuelve.
            p1.rootView
        }
    }
}