package com.code.kembang_telon.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.NewProductResponse

class HomeViewModel(private val repository: Repository) : ViewModel() {
    lateinit var allProduct: LiveData<Result<NewProductResponse>>

    fun getAllProduct() {
        allProduct = repository.getProduct()
    }
}