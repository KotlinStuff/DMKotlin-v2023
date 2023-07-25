package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products

class StoreRepository(val dataSource: StoreDataSource) {

    suspend fun fetchProducts(): List<Products> {
        val call = dataSource.getProducts()
        return if (call.isSuccessful)
            call.body()!!
        else emptyList()
    }

    suspend fun fetchCategories(): List<String> {
        val call = dataSource.getCategories()
        return if (call.isSuccessful)
            call.body()!!
        else emptyList()
    }

    suspend fun fetchProductsByCategory(category: String): List<Products> {
        val call = dataSource.getProductsByCategory(category)
        return if (call.isSuccessful)
            call.body()!!
        else emptyList()
    }

    suspend fun fetchProductById(id: Int): Products? {
        val call = dataSource.getProductById(id)
        return if (call.isSuccessful)
            call.body()
        else null
    }

    suspend fun login(user: String, pass: String): Login {
        val call = dataSource.login(user, pass)
        return if (call.isSuccessful)
            call.body()!!
        else Login()
    }
}