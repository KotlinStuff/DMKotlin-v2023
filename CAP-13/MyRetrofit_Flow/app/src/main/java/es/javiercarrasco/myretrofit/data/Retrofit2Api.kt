package es.javiercarrasco.myretrofit.data

import es.javiercarrasco.myretrofit.domain.model.Login
import es.javiercarrasco.myretrofit.domain.model.Products
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("username") user: String, @Field("password") pass: String): Login
}