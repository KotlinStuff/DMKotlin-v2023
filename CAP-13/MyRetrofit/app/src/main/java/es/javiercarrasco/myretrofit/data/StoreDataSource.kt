package es.javiercarrasco.myretrofit.data

import kotlinx.coroutines.flow.flow

class StoreDataSource : FakestoreapiFlow {

    //    suspend fun getProducts(): Result<List<Products>> {
//
//        val response = CoroutineScope(Dispatchers.IO).async {
//            return@async Retrofit2Api.getRetrofit().getProducts()
//            throw Exception()
//        }
//
//        try {
//            return Result.Success(response.await())
//        } catch (e: Exception) {
//            return Result.Error(e)
//        }
//    }
    override fun getProducts() = flow {
        emit(Retrofit2Api.getRetrofit().getProducts())
    }
}