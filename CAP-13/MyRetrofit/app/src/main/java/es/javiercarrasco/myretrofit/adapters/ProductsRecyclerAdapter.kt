package es.javiercarrasco.myretrofit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.javiercarrasco.myretrofit.R
import es.javiercarrasco.myretrofit.databinding.ItemProductBinding
import es.javiercarrasco.myretrofit.domain.model.Products

class ProductsRecyclerAdapter(private val onClickProduct: (Products) -> Unit) :
    ListAdapter<Products, ProductsViewHolder>(ProductsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currentWord = getItem(position)
        holder.bind(currentWord, onClickProduct)
    }
}

class ProductsDiffCallback : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }
}

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bind = ItemProductBinding.bind(view)

    fun bind(product: Products, onClickWords: (Products) -> Unit) {
        val context = bind.root.context
        bind.title.text = product.title
        bind.category.text = product.category
        bind.price.text = context.getString(R.string.txtPrecio, product.price)

        bind.imageView.contentDescription = product.title
        Glide.with(itemView)
            .load(product.image)
            .into(bind.imageView)

        itemView.setOnClickListener { onClickWords(product) }
    }
}