package com.code.kembang_telon.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.RegisterResponse
import com.code.kembang_telon.model.RegisterRequestBody

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    lateinit var registerPost: LiveData<Result<RegisterResponse>>

    fun postRegister(data: RegisterRequestBody) {
        registerPost = repository.register(data)
    }
}