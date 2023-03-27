package es.javiercarrasco.mysqlite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.mysqlite.data.model.SuperHero
import es.javiercarrasco.mysqlite.databinding.ItemRecyclerviewBinding

class SupersRecyclerAdapter : ListAdapter<SuperHero, SupersViewHolder>(SupersDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupersViewHolder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SupersViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SupersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SupersDiffCallback : DiffUtil.ItemCallback<SuperHero>() {
    override fun areItemsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean {
        return oldItem == newItem
    }
}

class SupersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemRecyclerviewBinding.bind(view)

    fun bind(superHero: SuperHero) {
        binding.tvSuperName.text = superHero.superName
        binding.tvRealName.text = superHero.realName
        binding.tvEditorial.text = superHero.editorial.name
    }
}