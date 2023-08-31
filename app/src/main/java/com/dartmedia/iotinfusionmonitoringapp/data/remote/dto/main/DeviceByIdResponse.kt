package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main

import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DataDeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceResultDeviceById
import com.google.gson.annotations.SerializedName

data class DeviceByIdResponse(

	@field:SerializedName("data")
	val data: DataDeviceByIdResponse? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	fun toDomain(): DeviceById {
		return DeviceById(
			DataDeviceById(
				DeviceResultDeviceById(
					data?.device?.roomName,
					data?.device?.deviceId,
					data?.device?.name,
					data?.device?.noBad,
					data?.device?.noRm,
					data?.device?.status
				)
			),
			status
		)
	}
}

data class DeviceResultDeviceByIdResponse(

	@field:SerializedName("room_name")
	val roomName: String? = null,

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("no_bad")
	val noBad: Int? = null,

	@field:SerializedName("no_rm")
	val noRm: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataDeviceByIdResponse(

	@field:SerializedName("device")
	val device: DeviceResultDeviceByIdResponse? = null
)
