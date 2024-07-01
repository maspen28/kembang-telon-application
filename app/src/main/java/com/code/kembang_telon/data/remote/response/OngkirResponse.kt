package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class OngkirResponse(

	@field:SerializedName("OngkirResponse")
	val ongkirResponse: List<OngkirResponseItem?>? = null
)

data class OngkirResponseItem(

	@field:SerializedName("costs")
	val costs: List<CostsItem?>? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class CostsItem(

	@field:SerializedName("cost")
	val cost: List<CostItem?>? = null,

	@field:SerializedName("service")
	val service: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)

data class CostItem(

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("etd")
	val etd: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
