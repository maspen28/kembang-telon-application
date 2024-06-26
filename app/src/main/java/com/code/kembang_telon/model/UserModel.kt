package com.code.kembang_telon.model

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val alamat: String,
    val username: String,
    val phoneNumber: String?,
    val isLogin: Boolean,
)