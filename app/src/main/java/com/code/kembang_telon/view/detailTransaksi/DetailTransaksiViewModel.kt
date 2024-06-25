package com.code.kembang_telon.view.detailTransaksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Repository

class DetailTransaksiViewModel(private val repository: Repository) : ViewModel() {
    val allproducts: LiveData<List<ProductEntity>>

    init {
        allproducts = repository.getAllCartProducts()
    }

}