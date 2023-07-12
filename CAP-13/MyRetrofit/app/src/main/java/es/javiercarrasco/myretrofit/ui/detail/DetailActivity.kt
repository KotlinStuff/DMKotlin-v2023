package es.javiercarrasco.myretrofit.ui.detail

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import es.javiercarrasco.myretrofit.R
import es.javiercarrasco.myretrofit.data.StoreDataSource
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val vm: DetailViewModel by viewModels {
        val storeDataSource = StoreDataSource()
        val storeRepository = StoreRepository(storeDataSource)
        val prodId = intent.getIntExtra(PRODUCT_ID, -1)
        DetailViewModelFactory(storeRepository, prodId)
    }

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"

        fun navigateToDetail(activity: AppCompatActivity, prodId: Int = -1) {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, prodId)
            }
            activity.startActivity(
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect {
                    binding.title.text = it.title
                    binding.description.text = it.description
                    binding.category.text = it.category
                    binding.price.text = getString(R.string.txtPrecio, it.price)

                    binding.imageView.contentDescription = it.title
                    Glide.with(this@DetailActivity)
                        .load(it.image)
                        .into(binding.imageView)
                }
            }
        }
    }
}