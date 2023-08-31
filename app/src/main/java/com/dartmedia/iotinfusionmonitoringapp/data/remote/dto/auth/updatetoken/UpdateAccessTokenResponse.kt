package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.updatetoken

import com.google.gson.annotations.SerializedName

data class UpdateAccessTokenResponse(

    @field:SerializedName("data")
	val data: DataUpdateAccessTokenResponse? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: String? = null

)

data class DataUpdateAccessTokenResponse(

	@field:SerializedName("accessToken")
	val accessToken: String? = null

)
