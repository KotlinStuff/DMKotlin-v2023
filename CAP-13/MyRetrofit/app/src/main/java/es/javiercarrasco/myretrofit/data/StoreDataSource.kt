package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.flow

class StoreDataSource : FakestoreApiFlow {

    override fun getProducts() = flow {
        emit(Retrofit2Api.getRetrofit().getProducts())
    }

    override fun getCategories() = flow {
        emit(Retrofit2Api.getRetrofit().getCategories())
    }

    override fun getProductsByCategory(category: String) = flow {
        emit(Retrofit2Api.getRetrofit().getProductsByCategory(category))
    }

    override suspend fun getProductById(id: Int): Products? {
        return Retrofit2Api.getRetrofit().getProductById(id)
    }
}