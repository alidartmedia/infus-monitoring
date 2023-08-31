package com.dartmedia.iotinfusionmonitoringapp.data.remote.api

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.updatetoken.UpdateAccessTokenRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.updatetoken.UpdateAccessTokenResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {

    @POST("auth/guard")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @PUT("auth/guard")
    suspend fun updateAccessToken(@Body updateAccessTokenRequest: UpdateAccessTokenRequest): Response<UpdateAccessTokenResponse>

    @HTTP(method = "DELETE", path = "auth/guard", hasBody = true)
    suspend fun logout(@Body logoutRequest: LogoutRequest): Response<LogoutResponse>

}