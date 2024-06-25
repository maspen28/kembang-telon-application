package com.code.kembang_telon.data.remote.retrofit


import com.code.kembang_telon.data.remote.response.DetailProductResponse
import com.code.kembang_telon.data.remote.response.LoginResponse
import com.code.kembang_telon.data.remote.response.ProductResponse
import com.code.kembang_telon.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("product")
        fun getAllProduct(): Call<ProductResponse>

    @GET("product/{id}")
    fun getDetailProduct(
        @Path("id") id: String,
    ): Call<DetailProductResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Call<LoginResponse>

    @POST("register")
    @FormUrlEncoded
    fun register(@Field("name") name: String, @Field("email") email: String, @Field("address") address: String, @Field("username") username: String, @Field("password") password: String): Call<RegisterResponse>

}