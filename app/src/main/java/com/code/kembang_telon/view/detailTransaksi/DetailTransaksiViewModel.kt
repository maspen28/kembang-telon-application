package com.code.kembang_telon.view.detailTransaksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.CartResponse
import com.code.kembang_telon.data.remote.response.CheckoutResponse
import com.code.kembang_telon.data.remote.response.CityResponse
import com.code.kembang_telon.data.remote.response.DistrictResponse
import com.code.kembang_telon.data.remote.response.OngkirResponseItem
import com.code.kembang_telon.data.remote.response.PaymentResponse
import com.code.kembang_telon.data.remote.response.ProvincesResponseItem

class DetailTransaksiViewModel(private val repository: Repository) : ViewModel() {

    private val _allCart = MutableLiveData<Result<CartResponse>>()
    val allCart: LiveData<Result<CartResponse>> get() = _allCart

    lateinit var provinces: LiveData<Result<List<ProvincesResponseItem>>>

    private val _listCity = MutableLiveData<Result<CityResponse>>()
    val listCity: LiveData<Result<CityResponse>> get() = _listCity

    private val _listDistrict = MutableLiveData<Result<DistrictResponse>>()
    val listDistrict: LiveData<Result<DistrictResponse>> get() = _listDistrict

    private val _ongkir = MutableLiveData<Result<List<OngkirResponseItem>>>()
    val ongkir: LiveData<Result<List<OngkirResponseItem>>> get() = _ongkir

    private val _checkout = MutableLiveData<Result<CheckoutResponse>>()
    val checkout: LiveData<Result<CheckoutResponse>> get() = _checkout

    private val _payment = MutableLiveData<Result<PaymentResponse>>()
    val payment: LiveData<Result<PaymentResponse>> get() = _payment


    fun payment(orderId: String) {
        repository.payment(orderId).observeForever { result ->
            _payment.value = result
        }
    }

    fun checkout(customer_id: String, province_id: String, city_id: String, district_id: String, ongkos_kirim: String, customer_phone: String) {
        repository.checkout(customer_id, province_id, city_id, district_id, ongkos_kirim, customer_phone).observeForever { result ->
            _checkout.value = result
        }
    }

    fun getOngkir(origin: String, destination: String, weight: String, courier: String) {
        repository.ongkir(origin, destination, weight, courier).observeForever { result ->
            _ongkir.value = result
        }
    }

    fun getProvinces() {
        provinces = repository.getProvinces()
    }

    fun getCity(province_id: String) {
        repository.getCity(province_id).observeForever { result ->
            _listCity.value = result
        }
    }

    fun getDistrict(province_id: String, city_id: String) {
        repository.getDistrict(province_id, city_id).observeForever { result ->
            _listDistrict.value = result
        }
    }

    fun getCart(customer_id: String) {
        repository.getCart(customer_id).observeForever { result ->
            _allCart.value = result
        }
    }

}