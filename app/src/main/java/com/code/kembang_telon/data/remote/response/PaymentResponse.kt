package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

	@field:SerializedName("snap_token")
	val snapToken: String? = null
)
