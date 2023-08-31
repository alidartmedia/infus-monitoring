package com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout

import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Logout
import com.google.gson.annotations.SerializedName

data class LogoutResponse(

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null

) {
    fun toDomain(): Logout {
        return Logout(
            status,
            message
        )
    }
}
