package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient

import com.google.gson.annotations.SerializedName

data class AddPatientResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
