package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.Flow

class StoreRepository(val dataSource: StoreDataSource) {

    fun fetchProducts(): Flow<List<Products>> {
        return dataSource.getProducts()
    }
}