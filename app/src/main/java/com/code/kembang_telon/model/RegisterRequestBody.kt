package com.code.kembang_telon.model

data class RegisterRequestBody (
    val name: String,
    val email: String,
    val address: String,
    val username: String,
    val password: String,

)