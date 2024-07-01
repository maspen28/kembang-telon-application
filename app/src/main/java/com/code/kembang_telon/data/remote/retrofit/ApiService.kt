package com.code.kembang_telon.data.remote.retrofit


import com.code.kembang_telon.data.remote.response.CartResponse
import com.code.kembang_telon.data.remote.response.CheckoutResponse
import com.code.kembang_telon.data.remote.response.CityResponse
import com.code.kembang_telon.data.remote.response.DeleteCartResponse
import com.code.kembang_telon.data.remote.response.DetailProductResponse
import com.code.kembang_telon.data.remote.response.DistrictResponse
import com.code.kembang_telon.data.remote.response.HistoryResponse
import com.code.kembang_telon.data.remote.response.LoginResponse
import com.code.kembang_telon.data.remote.response.OngkirResponseItem
import com.code.kembang_telon.data.remote.response.PaymentResponse
import com.code.kembang_telon.data.remote.response.PostCartResponse
import com.code.kembang_telon.data.remote.response.ProductResponse
import com.code.kembang_telon.data.remote.response.ProvincesResponseItem
import com.code.kembang_telon.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("history")
    fun getHistory(
        @Query("customer_id") customer_id: String,
    ): Call<HistoryResponse>

    @POST("payment/create/{orderId}")
    fun payment(
        @Path("orderId") orderId: String,
    ): Call<PaymentResponse>
    @POST("checkout")
    @FormUrlEncoded
    fun checkout(
        @Field("customer_id") customer_id: String,
        @Field("province_id") province_id: String,
        @Field("city_id") city_id: String,
        @Field("district_id") district_id: String,
        @Field("ongkos_kirim") ongkos_kirim: String,
        @Field("customer_phone") customer_phone: String,
    ): Call<CheckoutResponse>

    @POST("shipping/cost")
    @FormUrlEncoded
    fun ongkir(
        @Field("origin") origin: String,
        @Field("destination") destination: String,
        @Field("weight") weight: String,
        @Field("courier") courier: String
    ): Call<List<OngkirResponseItem>>

    @GET("provinces")
    fun getProvinces(): Call<List<ProvincesResponseItem>>

    @GET("city/{province_id}")
    fun getCity(
        @Path("province_id") province_id: String,
    ): Call<CityResponse>

    @GET("district/province/{province_id}/city/{city_id}")
    fun getDistrict(
        @Path("province_id") province_id: String,
        @Path("city_id") city_id: String,
    ): Call<DistrictResponse>

    @GET("product")
        fun getAllProduct(): Call<ProductResponse>

    @GET("product/{id}")
    fun getDetailProduct(
        @Path("id") id: String,
    ): Call<DetailProductResponse>

    @GET("cart")
    fun getCart(
        @Query("customer_id") customer_id: String,
    ): Call<CartResponse>

    @POST("cart")
    @FormUrlEncoded
    fun postCart(@Field("product_id") product_id: String, @Field("customer_id") customer_id: String, @Field("qty") qty: String): Call<PostCartResponse>

    @DELETE("cart")
    fun deleteCart(@Query("id") id: String,): Call<DeleteCartResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Call<LoginResponse>

    @POST("register")
    @FormUrlEncoded
    fun register(@Field("name") name: String, @Field("email") email: String, @Field("address") address: String, @Field("username") username: String, @Field("password") password: String): Call<RegisterResponse>

}