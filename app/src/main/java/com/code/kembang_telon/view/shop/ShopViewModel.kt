package com.code.kembang_telon.view.shop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel(private val repository: Repository) : ViewModel() {
    val allproducts: LiveData<List<ProductEntity>>

    init {
        allproducts = repository.getAllCartProducts()
    }


    fun deleteCart(product: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(product)
        }
    }

    fun updateCart(product: ProductEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(product)
        }
    }

}