package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// https://fakestoreapi.com/docs

class Retrofit2Api {
    companion object {
        fun getRetrofit(): FakestoreApi {
            return Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FakestoreApi::class.java)
        }
    }
}

interface FakestoreApi {
    @GET("products")
    suspend fun getProducts(): List<Products>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Products>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Products?
}

interface FakestoreApiFlow {
    fun getProducts(): Flow<List<Products>>
    fun getCategories(): Flow<List<String>>
    fun getProductsByCategory(category: String): Flow<List<Products>>
    suspend fun getProductById(id: Int): Products?
}