package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main

import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceCount
import com.google.gson.annotations.SerializedName

data class AllDeviceResponse(

	@field:SerializedName("totalData")
	val totalData: Int? = null,

	@field:SerializedName("previousPage")
	val previousPage: Int? = null,

	@field:SerializedName("data")
	val data: DataAllDeviceResponse? = null,

	@field:SerializedName("totalPage")
	val totalPage: Int? = null,

	@field:SerializedName("nextPage")
	val nextPage: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	fun toDomainDeviceCount(): DeviceCount {
		return DeviceCount(
			totalData!!
		)
	}
}

data class ProductsItemAllDeviceResponse(

	@field:SerializedName("room_name")
	val roomName: String? = null,

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("no_bad")
	val noBad: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataAllDeviceResponse(

	@field:SerializedName("products")
	val products: List<ProductsItemAllDeviceResponse>
)
