package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("data")
	val data: List<DataCart?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataCart(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("nama_produk")
	val namaProduk: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("cart_id")
	val id: Int? = null,

	@field:SerializedName("discount_id")
	val diskonId: String? = null,

	@field:SerializedName("discount_name")
	val nameDiskon: String? = null,

	@field:SerializedName("besar_diskon")
	val besarDiskon: String? = null,
)
