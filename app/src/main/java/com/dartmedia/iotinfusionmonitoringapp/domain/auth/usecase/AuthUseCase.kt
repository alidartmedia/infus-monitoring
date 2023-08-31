package com.dartmedia.iotinfusionmonitoringapp.domain.auth.usecase

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutRequest
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Login
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Logout
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    suspend fun login(loginRequest: LoginRequest): Flow<ResultState<Login>>

    suspend fun logout(logoutRequest: LogoutRequest): Flow<ResultState<Logout>>

}