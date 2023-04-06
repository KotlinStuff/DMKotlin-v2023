package es.javiercarrasco.myroom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.myroom.R
import es.javiercarrasco.myroom.data.model.SuperHero
import es.javiercarrasco.myroom.data.model.SupersWithEditorial
import es.javiercarrasco.myroom.databinding.ItemRecyclerviewBinding

class SupersRecyclerAdapter(
    private val onSuperHeroClick: (SuperHero) -> Unit,
    private val onSuperHeroLongClick: (SuperHero) -> Unit,
    private val onFabClick: (SuperHero) -> Unit
) : ListAdapter<SupersWithEditorial, SupersViewHolder>(SupersDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupersViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SupersViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SupersViewHolder, position: Int) {
        holder.bind(getItem(position), onSuperHeroClick, onSuperHeroLongClick, onFabClick)
    }
}

// DiffUtil.ItemCallback permite calcular las diferencias entre dos objetos no nulos de la lista.
class SupersDiffCallback : DiffUtil.ItemCallback<SupersWithEditorial>() {
    override fun areItemsTheSame(
        oldItem: SupersWithEditorial,
        newItem: SupersWithEditorial
    ): Boolean {
        return oldItem.supers.idSuper == newItem.supers.idSuper
    }

    override fun areContentsTheSame(
        oldItem: SupersWithEditorial,
        newItem: SupersWithEditorial
    ): Boolean {
        return oldItem == newItem
    }
}

class SupersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemRecyclerviewBinding.bind(view)

    fun bind(
        superHero: SupersWithEditorial,
        onSuperHeroClick: (SuperHero) -> Unit,
        onSuperHeroLongClick: (SuperHero) -> Unit,
        onFabClick: (SuperHero) -> Unit
    ) {
        binding.tvSuperName.text = superHero.supers.superName
        binding.tvRealName.text = superHero.supers.realName
        binding.tvEditorial.text = superHero.editorial.name

        binding.ivFab.setImageState(
            intArrayOf(R.attr.state_fab_on), superHero.supers.favorite == 1
        )

        itemView.setOnClickListener { onSuperHeroClick(superHero.supers) }
        itemView.setOnLongClickListener {
            onSuperHeroLongClick(superHero.supers)
            true
        }
        binding.ivFab.setOnClickListener { onFabClick(superHero.supers) }
    }
}