package es.javiercarrasco.myrecyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import es.javiercarrasco.myrecyclerview.databinding.ItemAnimalListBinding

class RecyclerAdapter(animalsList: MutableList<MyAnimal>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var animals: MutableList<MyAnimal>

    // Se instancia la interface para el LongClick.
    private lateinit var mLongClickListener: ItemLongClickListener

    // Interface que debe implementar la clase que use el adaptador.
    interface ItemLongClickListener {
        // Este método recibe la vista sobre la que se ha pulsado y la posición.
        fun onItemLongClick(view: View, position: Int)
    }

    // Método encargado de capturar el evento, se instancia en el adaptar.
    fun setLongClickListener(itemLongClickListener: ItemLongClickListener?) {
        mLongClickListener = itemLongClickListener!!
    }

    // Constructor inicial de la clase. Se pasa la fuente de datos y el contexto
    // sobre el que se mostrará.
    init {
        this.animals = animalsList
    }

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

    // Método encargado de pasar los objetos, uno a uno, al ViewHolder
    // personalizado.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = animals.get(position)
        holder.bind(item)
    }

    // Devuelve el tamaño de la fuente de datos.
    override fun getItemCount(): Int {
        return animals.size
    }

    // Esta clase interna se encarga de rellenar cada una de las vistas que
    // se inflarán para cada uno de los elementos del RecyclerView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Se usa View Binding para localizar los elementos en la vista.
        private val binding = ItemAnimalListBinding.bind(view)

        fun bind(animal: MyAnimal) {
            binding.tvNameAnimal.text = animal.animalName
            binding.tvLatinName.text = animal.latinName

            Glide.with(binding.root)
                .load(animal.imageAnimal!!)
                .override(150, 150)
                // Centra la imagen y redondea las esquinas.
                .transform(CenterCrop(),RoundedCorners(10))
                .into(binding.ivAnimalImage)

            itemView.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    animal.animalName,
                    Toast.LENGTH_SHORT
                ).show()
            }

            itemView.setOnLongClickListener {
                binding.root.setBackgroundColor(Color.RED)
                mLongClickListener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }
}