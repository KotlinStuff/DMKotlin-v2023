package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Products
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Retrofit2Api {
    companion object {
        fun getRetrofit(): Fakestoreapi {
            return Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Fakestoreapi::class.java)
        }
    }
}

interface Fakestoreapi {
    @GET("products")
    suspend fun getProducts(): List<Products>
}

interface FakestoreapiFlow {
    fun getProducts(): Flow<List<Products>>
}