package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(

	@field:SerializedName("snap_token")
	val snapToken: String? = null,

	@field:SerializedName("customer_address")
	val customerAddress: String? = null,

	@field:SerializedName("id_order")
	val idOrder: Int? = null,

	@field:SerializedName("cost")
	val cost: Int? = null,

	@field:SerializedName("ongkos_kirim")
	val ongkosKirim: String? = null,

	@field:SerializedName("customer_phone")
	val customerPhone: String? = null,

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("customer_name")
	val customerName: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
