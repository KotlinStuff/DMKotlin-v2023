package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class StoreDataSource : FakestoreApi {
    private val api = Retrofit2Api.getRetrofit()

    override suspend fun getProducts(): Response<List<Products>> {
        return api.getProducts()
    }

    override suspend fun getCategories(): Response<List<String>> {
        return api.getCategories()
    }

    override suspend fun getProductsByCategory(category: String): Response<List<Products>> {
        return api.getProductsByCategory(category)
    }

    override suspend fun getProductById(id: Int): Response<Products?> {
        return api.getProductById(id)
    }

    override suspend fun login(user: String, pass: String): Response<Login> {
        return api.login(user, pass)
    }

//    fun getProducts() = flow {
//        emit(Retrofit2Api.getRetrofit().getProducts())
//    }
//
//    fun getCategories() = flow {
//        emit(Retrofit2Api.getRetrofit().getCategories())
//    }
//
//    fun getProductsByCategory(category: String) = flow {
//        emit(Retrofit2Api.getRetrofit().getProductsByCategory(category))
//    }
//
//
//    suspend fun getProductById(id: Int): Products? {
//        return Retrofit2Api.getRetrofit().getProductById(id)
//    }
//
//    suspend fun login(user: String, pass: String): Login {
//        return Retrofit2Api.getRetrofit().login(user, pass)
//    }
}