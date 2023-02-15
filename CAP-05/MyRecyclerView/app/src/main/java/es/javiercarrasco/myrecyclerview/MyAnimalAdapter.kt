package es.javiercarrasco.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import es.javiercarrasco.myrecyclerview.databinding.ItemAnimalListBinding

class MyAnimalAdapter(val animalsList: MutableList<MyAnimal>) :
    RecyclerView.Adapter<MyAnimalAdapter.ViewHolder>() {

    // Es el encargado de devolver el ViewHolder ya configurado.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAnimalAdapter.ViewHolder {
        return ViewHolder(
            ItemAnimalListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    // Método encargado de pasar los objetos, uno a uno, al ViewHolder personalizado.
    override fun onBindViewHolder(
        holder: MyAnimalAdapter.ViewHolder,
        position: Int
    ) {
        val item = animalsList[position]
        holder.bind(item)
    }

    // Devuelve el tamaño de la fuente de datos.
    override fun getItemCount(): Int {
        return animalsList.size
    }

    // Esta clase interna se encarga de rellenar cada una de las vistas que
    // se inflarán para cada uno de los elementos del RecyclerView.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Se usa View Binding para localizar los elementos en la vista.
        private val binding = ItemAnimalListBinding.bind(view)

        fun bind(animal: MyAnimal) {
            binding.tvNameAnimal.text = animal.animalName
            binding.tvLatinName.text = animal.latinName

//            binding.ivAnimalImage.setImageResource(animal.imageAnimal)

            Glide.with(binding.root)
                .load(animal.imageAnimal)
                .override(150, 150)
                // Centra la imagen y redondea las esquinas.
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.ivAnimalImage)

            // Listener sobre la vista.
            itemView.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    animal.animalName,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}