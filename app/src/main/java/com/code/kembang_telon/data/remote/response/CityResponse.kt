package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class CityResponse(

	@field:SerializedName("province")
	val province: Province,

	@field:SerializedName("cities")
	val cities: List<CitiesItem>
)

data class Province(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class CitiesItem(

	@field:SerializedName("province_id")
	val provinceId: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
