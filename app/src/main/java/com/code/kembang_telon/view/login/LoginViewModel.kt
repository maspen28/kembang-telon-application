package com.code.kembang_telon.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.LoginResponse
import com.code.kembang_telon.model.LoginRequestBody

class LoginViewModel(private val repository: Repository) : ViewModel() {
    lateinit var loginPost: LiveData<Result<LoginResponse>>

    fun postLogin(data: LoginRequestBody) {
        loginPost = repository.login(data)
    }
}