package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient

data class AddPatientRequest(
    val name: String,
    val nameRoom: String,
    val noBad: Int,
    val noRm: String,
    val deviceId: String
)
