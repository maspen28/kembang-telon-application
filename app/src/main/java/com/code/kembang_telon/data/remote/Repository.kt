package com.code.kembang_telon.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.kembang_telon.data.local.room.CardDao
import com.code.kembang_telon.data.local.room.ProductDao
import com.code.kembang_telon.data.remote.response.CartResponse
import com.code.kembang_telon.data.remote.response.CheckoutResponse
import com.code.kembang_telon.data.remote.response.CityResponse
import com.code.kembang_telon.data.remote.response.DeleteCartResponse
import com.code.kembang_telon.data.remote.response.DetailProductResponse
import com.code.kembang_telon.data.remote.response.DistrictResponse
import com.code.kembang_telon.data.remote.response.HistoryResponse
import com.code.kembang_telon.data.remote.response.LoginResponse
import com.code.kembang_telon.data.remote.response.OngkirResponseItem
import com.code.kembang_telon.data.remote.response.PaymentResponse
import com.code.kembang_telon.data.remote.response.PostCartResponse
import com.code.kembang_telon.data.remote.response.ProductResponse
import com.code.kembang_telon.data.remote.response.ProvincesResponseItem
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
//    fun getAllCartProducts(): LiveData<List<ProductEntity>> = productDao.getAll()
//
//     fun update(product: ProductEntity) {
//         executorService.execute{ productDao.update(product)}
//    }
//
//     fun delete(product: ProductEntity) {
//         executorService.execute{productDao.delete(product)}
//    }
//
//     fun insert(product: ProductEntity) {
//         executorService.execute{productDao.insert(product) }
//     }



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

    fun getHistory(customer_id: String): LiveData<Result<HistoryResponse>>{
        return makeApiCall(apiService.getHistory(customer_id))
    }
    fun payment(orderId: String): LiveData<Result<PaymentResponse>>{
        return makeApiCall(apiService.payment(orderId))
    }

    fun checkout(customer_id: String, province_id: String, city_id: String, district_id: String, ongkos_kirim: String, customer_phone: String ) : LiveData<Result<CheckoutResponse>>{
        return makeApiCall(apiService.checkout(customer_id, province_id, city_id, district_id, ongkos_kirim, customer_phone))
    }

    fun ongkir(origin: String, destination: String, weight: String, courier: String): LiveData<Result<List<OngkirResponseItem>>>{
        return makeApiCall(apiService.ongkir(origin, destination, weight, courier))
    }

    fun getProvinces(): LiveData<Result<List<ProvincesResponseItem>>>{
        return makeApiCall(apiService.getProvinces())
    }

    fun getCity(province_id: String): LiveData<Result<CityResponse>>{
        return makeApiCall(apiService.getCity(province_id))
    }

    fun getDistrict(province_id: String, city_id: String): LiveData<Result<DistrictResponse>>{
        return makeApiCall(apiService.getDistrict(province_id, city_id))
    }

    fun getCart(customer_id: String): LiveData<Result<CartResponse>>{
        return makeApiCall(apiService.getCart(customer_id))
    }

    fun insertCart(product_id: String, customer_id: String, qty: String): LiveData<Result<PostCartResponse>>{
        return makeApiCall(apiService.postCart(product_id, customer_id, qty))
    }

    fun deleteCart(id: String): LiveData<Result<DeleteCartResponse>>{
        return makeApiCall(apiService.deleteCart(id))
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