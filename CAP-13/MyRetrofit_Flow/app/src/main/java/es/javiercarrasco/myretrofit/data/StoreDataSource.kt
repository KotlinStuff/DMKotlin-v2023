package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.flow

class StoreDataSource { //: FakestoreApiFlow {
    private val retrofit2Api = Retrofit2Api.getRetrofit()

    fun getProducts() = flow {
        emit(retrofit2Api.getProducts())
    }

    fun getCategories() = flow {
        emit(retrofit2Api.getCategories())
    }

    fun getProductsByCategory(category: String) = flow {
        emit(retrofit2Api.getProductsByCategory(category))
    }

    suspend fun getProductById(id: Int): Products? {
        return retrofit2Api.getProductById(id)
    }

    suspend fun login(user: String, pass: String): Login {
        return retrofit2Api.login(user, pass)
    }
}