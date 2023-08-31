package com.dartmedia.iotinfusionmonitoringapp.domain.auth.model

data class Login(
    val status: String? = null,
    val data: DataLogin? = null
)

data class DataLogin(
    val accessToken: String? = null,
    val refreshToken: String? = null
)
