package com.code.kembang_telon.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.HistoryResponse

class HistoryShopViewModel(private val repository: Repository) : ViewModel()  {

    private val _history = MutableLiveData<Result<HistoryResponse>>()
    val history: LiveData<Result<HistoryResponse>> get() = _history
    fun getHistory(customerId: String) {
        repository.getHistory(customerId).observeForever { result ->
            _history.value = result
        }
    }
}