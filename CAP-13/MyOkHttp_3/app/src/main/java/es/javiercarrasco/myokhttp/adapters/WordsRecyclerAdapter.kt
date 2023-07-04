package es.javiercarrasco.myokhttp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.myokhttp.model.Words

class WordsRecyclerAdapter(private val onClickWords: (Words) -> Unit) :
    ListAdapter<Words, WordsViewHolder>(WordsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        return WordsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        val currentWord = getItem(position)
        holder.bind(currentWord, onClickWords)
    }
}

class WordsDiffCallback : DiffUtil.ItemCallback<Words>() {
    override fun areItemsTheSame(oldItem: Words, newItem: Words): Boolean {
        return oldItem.idPalabra == newItem.idPalabra
    }

    override fun areContentsTheSame(oldItem: Words, newItem: Words): Boolean {
        return oldItem == newItem
    }
}

class WordsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val wordItemView: TextView = view.findViewById(android.R.id.text1)
    private val wordItemView2: TextView = view.findViewById(android.R.id.text2)

    fun bind(word: Words, onClickWords: (Words) -> Unit) {
        wordItemView.text = word.idPalabra
        wordItemView2.text = word.palabra

        itemView.setOnClickListener { onClickWords(word) }
    }
}
