package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class DistrictResponse(

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("districts")
	val districts: List<DistrictsItem>
)

data class DistrictsItem(

	@field:SerializedName("postalcode")
	val postalcode: Any? = null,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
