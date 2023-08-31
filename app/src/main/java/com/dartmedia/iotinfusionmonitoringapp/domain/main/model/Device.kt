package com.dartmedia.iotinfusionmonitoringapp.domain.main.model

import com.google.gson.annotations.SerializedName

data class Device(
    val roomName: String? = null,
    val deviceId: String? = null,
    val noBad: Int? = null,
    val status: Int? = null
)
