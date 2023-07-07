package es.javiercarrasco.myretrofit.data

import kotlinx.coroutines.flow.flow

class StoreDataSource : FakestoreApiFlow {

    override fun getProducts() = flow {
        emit(Retrofit2Api.getRetrofit().getProducts())
    }
}