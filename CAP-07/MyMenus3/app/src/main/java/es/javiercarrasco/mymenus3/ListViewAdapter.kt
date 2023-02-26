package es.javiercarrasco.mymenus3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import es.javiercarrasco.mymenus3.databinding.ItemLayoutBinding

class ListViewAdapter(
    var names: List<String>
) : BaseAdapter() {

    override fun getView(
        position: Int,
        convertView: View?, parent: ViewGroup?
    ): View {
        val bindingFila: ItemLayoutBinding
        // Es "infla" la vista.
        if (convertView == null) {
            bindingFila = ItemLayoutBinding.inflate(
                LayoutInflater.from(parent!!.context),
                parent,
                false
            )
        } else bindingFila = ItemLayoutBinding.bind(convertView)

        // Se asigna el nombre al TextView
        bindingFila.personName.text = this.getItem(position)

        // Se asigna como etiqueta del checkBox la posición en la que se encuentra.
        bindingFila.checkBox.tag = position

        if (MainActivity.isActionMode)
            bindingFila.checkBox.visibility = View.VISIBLE
        else bindingFila.checkBox.visibility = View.GONE

        // Se controla la selección del usuario mediante la lista selección.
        bindingFila.checkBox.setOnCheckedChangeListener { compoundButton, _ ->
            val pos: Int = compoundButton.tag.toString().toInt()
            Log.d("CHECKBOX", position.toString())

            // Se añade o elimina de la lista la selección.
            if (MainActivity.seleccion.contains(this.names[pos])) {
                MainActivity.seleccion.remove(this.names[pos])
            } else {
                MainActivity.seleccion.add(this.names[pos])
            }

            MainActivity.actionMode!!.title =
                "${MainActivity.seleccion.size} items seleccionados"
        }

        // Se devuelve la fila.
        return bindingFila.root
    }

    override fun getItem(position: Int): String {
        return this.names[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.names.size
    }

    fun eliminarNombres(items: List<String>) {
        // Se elimina los elementos seleccionados.
        for (item in items) {
            MainActivity.personas.remove(item)
        }

        // Se notifica un cambio en la información mostrada en la lista.
        // Esto producirá la actualización de la vista.
        notifyDataSetChanged()
    }
}