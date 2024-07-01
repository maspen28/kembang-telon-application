package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProvincesResponse(

	@field:SerializedName("ProvincesResponse")
	val provincesResponse: List<ProvincesResponseItem>
)

data class ProvincesResponseItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
