package com.code.kembang_telon.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.local.room.CardDao
import com.code.kembang_telon.data.local.room.ProductDao
import com.code.kembang_telon.data.remote.response.DetailProductResponse
import com.code.kembang_telon.data.remote.response.LoginResponse
import com.code.kembang_telon.data.remote.response.ProductResponse
import com.code.kembang_telon.data.remote.response.RegisterResponse
import com.code.kembang_telon.data.remote.retrofit.ApiService
import com.code.kembang_telon.model.LoginRequestBody
import com.code.kembang_telon.model.RegisterRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService

class Repository(
    private val apiService: ApiService,
    private val cardDao: CardDao,
    private val productDao: ProductDao,
    private val executorService: ExecutorService
    ) {

    // local
    fun getAllCartProducts(): LiveData<List<ProductEntity>> = productDao.getAll()

     fun update(product: ProductEntity) {
         executorService.execute{ productDao.update(product)}
    }

     fun delete(product: ProductEntity) {
         executorService.execute{productDao.delete(product)}
    }

     fun insert(product: ProductEntity) {
         executorService.execute{productDao.insert(product) }
     }



    // remote
    private fun <T> makeApiCall(apiCall: Call<T>): LiveData<Result<T>> {
        val result = MutableLiveData<Result<T>>()

        result.value = Result.Loading
        apiCall.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        result.value = Result.Success(items)

                    } else {
                        result.value = Result.Error("Data not found")
                    }
                } else {
                    result.value = Result.Error("Error ${response.code()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")

            }
        })

        return result
    }

    fun getProduct(): LiveData<Result<ProductResponse>>{
        return makeApiCall(apiService.getAllProduct())
    }

    fun getDetailProduct(id: String): LiveData<Result<DetailProductResponse>>{
        return makeApiCall(apiService.getDetailProduct(id))
    }

    fun login(data: LoginRequestBody): LiveData<Result<LoginResponse>>{
        return makeApiCall(apiService.login(data.email, data.password))
    }

    fun register(data: RegisterRequestBody): LiveData<Result<RegisterResponse>>{
        return makeApiCall(apiService.register(data.name, data.email, data.address, data.username, data.password))
    }


    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            cardDao: CardDao,
            productDao: ProductDao,
            executorService: ExecutorService


        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, cardDao, productDao, executorService)
            }.also { instance = it }
    }
}