package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login

import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.DataLogin
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Login
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: DataLoginResponse? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null

) {
    fun toDomain(): Login {
        return Login(
            status,
            DataLogin(data?.accessToken, data?.refreshToken)
        )
    }
}

data class DataLoginResponse(

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null

)
