package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.Flow

class StoreRepository(val dataSource: StoreDataSource) {

    fun fetchProducts(): Flow<List<Products>> {
        return dataSource.getProducts()
    }

    fun fetchCategories(): Flow<List<String>> {
        return dataSource.getCategories()
    }

    fun fetchProductsByCategory(category: String): Flow<List<Products>> {
        return dataSource.getProductsByCategory(category)
    }

    suspend fun fetchProductById(id: Int): Products? {
        return dataSource.getProductById(id)
    }
}