package es.javiercarrasco.myretrofit.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.javiercarrasco.myretrofit.R
import es.javiercarrasco.myretrofit.ShareApp
import es.javiercarrasco.myretrofit.adapters.ProductsRecyclerAdapter
import es.javiercarrasco.myretrofit.data.StoreDataSource
import es.javiercarrasco.myretrofit.data.StoreRepository
import es.javiercarrasco.myretrofit.databinding.ActivityMainBinding
import es.javiercarrasco.myretrofit.databinding.LoginLayoutBinding
import es.javiercarrasco.myretrofit.ui.detail.DetailActivity
import es.javiercarrasco.myretrofit.utils.checkConnection
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by viewModels {
        val storeDataSource = StoreDataSource()
        val storeRepository = StoreRepository(storeDataSource)
        MainViewModelFactory(storeRepository)
    }

    private val adapter: ProductsRecyclerAdapter by lazy {
        ProductsRecyclerAdapter(
            onClickProduct = { product ->
                DetailActivity.navigateToDetail(this@MainActivity, prodId = product.id)
            },
            onClickDelete = { product ->
                //vm.deleteProduct(product)
                Toast.makeText(
                    this@MainActivity,
                    "Delete ${product.title}", Toast.LENGTH_SHORT
                ).show()
                //adapter.notifyItemRemoved(adapter.currentList.indexOf(product))
                //collectProducts()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createMenu()

        binding.recyclerProducts.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerProducts.adapter = adapter

        if (checkConnection(this)) {
            collectCategories()
            collectProducts()
        }
    }


    override fun onStart() {
        super.onStart()

        binding.swipeRefresh.setOnRefreshListener {
            adapter.submitList(emptyList())

            if (checkConnection(this)) {
                collectCategories()
                collectProducts()
            }
            // Se desactiva el refresco.
            binding.swipeRefresh.isRefreshing = false
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            adapter.submitList(emptyList())
            when (item.itemId) {
                R.id.itemBottom1 -> { // All
                    vm.fetchProducts()
                }
                else -> {
                    vm.fetchProductsByCategory(item.title.toString())
                }
            }
            collectProducts()
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createMenu() {
        // Se añade el menú a la Toolbar persosnalizada.
        binding.mToolbar.inflateMenu(R.menu.action_bar_menu)

        stateLogin()

        binding.mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_login -> {
                    if (it.title == getString(R.string.txtLogin)) {
                        showLoginDialog()
                    } else {
                        ShareApp.preferences.token = ""
                        stateLogin()
                        binding.recyclerProducts.adapter?.notifyDataSetChanged()

                        Toast.makeText(
                            this@MainActivity,
                            "You are logged out", Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun stateLogin() {
        if (ShareApp.preferences.token.isBlank()) {
            binding.mToolbar.menu.findItem(R.id.item_login)
                .setIcon(R.drawable.ic_login)
                .title = getString(R.string.txtLogin)
        } else {
            binding.mToolbar.menu.findItem(R.id.item_login)
                .setIcon(R.drawable.ic_logout)
                .title = getString(R.string.txtLogout)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showLoginDialog() {
        val bindCustomDialog = LoginLayoutBinding.inflate(layoutInflater)

        val dialogLogin = MaterialAlertDialogBuilder(this).apply {
            setView(bindCustomDialog.root)
            setTitle(R.string.txtLogin)
            setPositiveButton(android.R.string.ok, null)
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
        }.create()

        dialogLogin.setOnShowListener {
            dialogLogin.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val user = bindCustomDialog.etUsername.text.toString().trim()
                val pass = bindCustomDialog.etPassword.text.toString().trim()

                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    vm.getLogin(this, user, pass)
                    lifecycleScope.launch {
                        vm.token.collect { login ->
                            ShareApp.preferences.token = login.token
                            stateLogin()
                            if (login.token != "") {
                                binding.recyclerProducts.adapter?.notifyDataSetChanged()
                                Toast.makeText(
                                    this@MainActivity,
                                    "You are logged\n\nToken ${login.token}", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    dialogLogin.dismiss()
                } else {
                    bindCustomDialog.etUsername.error = getString(R.string.txtErrorDialog)
                    bindCustomDialog.etPassword.error = getString(R.string.txtErrorDialog)
                }
            }
        }

        dialogLogin.show()
    }

    private fun collectProducts() {
        lifecycleScope.launch {
            vm.products
                .catch {
                    println("ERROR: $it")
                }
                .collect {
                    it.forEach { p ->
                        println("PRODUCTO: ${p.title}")
                    }
                    adapter.submitList(it)
                }
        }
    }

    private fun collectCategories() {
        lifecycleScope.launch {
            vm.categories
                .catch {
                    println("ERROR: $it")
                }
                .collect {
                    binding.bottomNavigation.menu.apply {
                        this.findItem(R.id.itemBottom2).title = it[0]
                        this.findItem(R.id.itemBottom3).title = it[1]
                        this.findItem(R.id.itemBottom4).title = it[2]
                        this.findItem(R.id.itemBottom5).title = it[3]
                    }
                }
        }
    }
}