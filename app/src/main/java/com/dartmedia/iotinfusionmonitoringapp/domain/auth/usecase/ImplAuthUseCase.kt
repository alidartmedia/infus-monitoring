package com.dartmedia.iotinfusionmonitoringapp.domain.auth.usecase

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutRequest
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Login
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Logout
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.repository.AuthRepository
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImplAuthUseCase @Inject constructor(private val authRepository: AuthRepository): AuthUseCase {

    override suspend fun login(loginRequest: LoginRequest): Flow<ResultState<Login>> {
        return authRepository.login(loginRequest)
    }

    override suspend fun logout(logoutRequest: LogoutRequest): Flow<ResultState<Logout>> {
        return authRepository.logout(logoutRequest)
    }

}