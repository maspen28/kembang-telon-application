package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("orders")
	val orders: List<OrdersItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class OrdersItem(

	@field:SerializedName("customer_address")
	val customerAddress: String? = null,

	@field:SerializedName("cost")
	val cost: Int? = null,

	@field:SerializedName("customer_phone")
	val customerPhone: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("status_label")
	val statusLabel: String? = null,

	@field:SerializedName("ref")
	val ref: Any? = null,

	@field:SerializedName("ongkos_kirim")
	val ongkosKirim: Int? = null,

	@field:SerializedName("shipping")
	val shipping: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("province_id")
	val provinceId: Int? = null,

	@field:SerializedName("subtotal")
	val subtotal: Int? = null,

	@field:SerializedName("ref_status")
	val refStatus: Int? = null,

	@field:SerializedName("tracking_number")
	val trackingNumber: Any? = null,

	@field:SerializedName("details")
	val details: List<DetailsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("customer_name")
	val customerName: String? = null,

	@field:SerializedName("district_id")
	val districtId: Int? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DetailsItem(

	@field:SerializedName("product")
	val product: Product? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("order_id")
	val orderId: Int? = null
)

data class Product(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
