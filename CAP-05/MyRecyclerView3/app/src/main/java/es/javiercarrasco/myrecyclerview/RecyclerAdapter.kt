package es.javiercarrasco.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myrecyclerview.databinding.ItemAnimalListBinding

class RecyclerAdapter(val animalsList: MutableList<MyAnimal>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // Es el encargado de devolver el ViewHolder ya configurado.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemAnimalListBinding.inflate(
                layoutInflater,
                parent,
                false
            ).root
        )
    }

    // Método encargado de pasar los objetos, uno a uno, al ViewHolder personalizado.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(animalsList.get(position))
    }

    // Devuelve el tamaño de la fuente de datos.
    override fun getItemCount() = animalsList.size

    // Esta clase interna se encarga de rellenar cada una de las vistas que
    // se inflarán para cada uno de los elementos del RecyclerView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Se usa View Binding para localizar los elementos en la vista.
        private val binding = ItemAnimalListBinding.bind(view)

        fun bind(animal: MyAnimal) {
            binding.tvNameAnimal.text = animal.animalName
            binding.tvLatinName.text = animal.latinName

            Glide.with(binding.root)
                .load(animal.imageAnimal)
                .override(150, 150)
                // Centra la imagen y redondea las esquinas.
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.ivAnimalImage)

            itemView.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    animal.animalName,
                    Toast.LENGTH_SHORT
                ).show()
            }

//            itemView.setOnLongClickListener {
//                Snackbar.make(
//                    binding.root,
//                    "¿Confirmas el borrado?",
//                    Snackbar.LENGTH_LONG
//                ).setAction("Sí") {
//                    animalsList.removeAt(adapterPosition)
//                    notifyItemRemoved(adapterPosition)
//                }.show()
//
//                true
//            }
        }
    }
}