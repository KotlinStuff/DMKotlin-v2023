package es.javiercarrasco.mybaseadapter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import es.javiercarrasco.mybaseadapter.R
import es.javiercarrasco.mybaseadapter.databinding.ItemListaBinding
import es.javiercarrasco.mybaseadapter.model.Curso
import java.text.FieldPosition

/**
 * Created by Javier Carrasco on 16/11/22.
 * App: My BaseAdapter
 */
class AdapterCursos(
    listaDatos: ArrayList<Curso>
) : BaseAdapter() {

    var datos = listaDatos

    // Devuelve el posición del ítem.
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    // Devuelve el tamaño de la fuente de datos.
    override fun getCount(): Int {
        return datos.size
    }

    // Devuelve el ítem según la posición.
    override fun getItem(position: Int): Any {
        return datos[position]
    }

    // Obtiene la vista inflada con los datos según su posición.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (convertView == null) {
            val viewBinding =
                ItemListaBinding.inflate(
                    LayoutInflater.from(parent!!.context),
                    parent,
                    false
                )

            viewBinding.tvSiglas.text = datos[position].siglas
            viewBinding.tvNombre.text = datos[position].nombre

            viewBinding.root
        } else convertView
    }
}