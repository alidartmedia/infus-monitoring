package com.dartmedia.iotinfusionmonitoringapp.domain.main.model

import com.google.gson.annotations.SerializedName


data class DeviceById(
    val data: DataDeviceById? = null,
    val status: String? = null
)

data class DeviceResultDeviceById(
    val roomName: String? = null,
    val deviceId: String? = null,
    val name: String? = null,
    val noBad: Int? = null,
    val noRm: String? = null,
    val status: Int? = null
)

data class DataDeviceById(
    val device: DeviceResultDeviceById? = null
)
