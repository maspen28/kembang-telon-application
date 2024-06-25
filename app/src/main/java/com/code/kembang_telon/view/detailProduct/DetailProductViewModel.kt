package com.code.kembang_telon.view.detailProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.DetailProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repository: Repository) : ViewModel() {
    lateinit var product: LiveData<Result<DetailProductResponse>>

    fun getProduct(id: String) {
        product = repository.getDetailProduct(id)
    }

    fun insert(product: ProductEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(product)
    }
}