package com.code.kembang_telon.view.shop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.CartResponse
import com.code.kembang_telon.data.remote.response.DeleteCartResponse

class ShopViewModel(private val repository: Repository) : ViewModel() {

    private val _allCart = MutableLiveData<Result<CartResponse>>()
    val allCart: LiveData<Result<CartResponse>> get() = _allCart

    private val _deleteCart = MutableLiveData<Result<DeleteCartResponse>>()

    val deleteCart: LiveData<Result<DeleteCartResponse>> get() = _deleteCart

    fun getCart(customer_id: String) {
        Log.e("dataerror", "MASUK KE GET DATA CART")
        repository.getCart(customer_id).observeForever { result ->
            _allCart.value = result
        }
    }

    fun deleteCart(id: String){
        Log.e("dataerror", "MASUK KE DELETE DATA CART")
        repository.deleteCart(id).observeForever { result ->
            _deleteCart.value = result
        }
    }

}