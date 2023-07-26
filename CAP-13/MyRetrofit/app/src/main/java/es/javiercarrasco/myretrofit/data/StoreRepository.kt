package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products
import retrofit2.Response

class StoreRepository(val dataSource: StoreDataSource) {

    suspend fun fetchProducts(): Response<List<Products>> {
        return dataSource.getProducts()
    }

    suspend fun fetchCategories(): Response<List<String>> {
        return dataSource.getCategories()
    }

    suspend fun fetchProductsByCategory(category: String): Response<List<Products>> {
        return dataSource.getProductsByCategory(category)
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