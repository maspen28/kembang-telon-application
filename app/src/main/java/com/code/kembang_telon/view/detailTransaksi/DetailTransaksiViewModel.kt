package com.code.kembang_telon.view.detailTransaksi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.CartResponse
import com.code.kembang_telon.data.remote.response.DeleteCartResponse

class DetailTransaksiViewModel(private val repository: Repository) : ViewModel() {

    private val _allCart = MutableLiveData<Result<CartResponse>>()
    val allCart: LiveData<Result<CartResponse>> get() = _allCart


    fun getCart(customer_id: String) {
        repository.getCart(customer_id).observeForever { result ->
            _allCart.value = result
        }
    }

}